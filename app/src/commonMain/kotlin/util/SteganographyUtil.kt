/*
 * PixelSafe - Steganography tool for PNG images
 * Copyright (C) 2025 Stefan Oltmann
 * https://stefan-oltmann.de/pixelsafe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package util

import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo

/**
 * Utility for steganography operations on images.
 *
 * This object provides functionality to hide and retrieve data within images
 * by manipulating the least significant bits of pixel color channels.
 */
object SteganographyUtil {

    private const val BITS_PER_BYTE = 8

    const val BYTE_COUNT_FOR_LENGTH = 4

    private const val TERMINATOR_BYTE = 0x00.toByte()

    /** The signature to be used to identify hidden data. */
    private val signatureBytes = byteArrayOf(80, 105, 120, 101, 108, 83, 97, 102, 101)

    private val terminatorBytes = byteArrayOf(TERMINATOR_BYTE)

    /** The length of the signature (9 bytes). */
    private val signatureLength = signatureBytes.size

    /** The length data starts after the signature (9 bytes) and is 32 bits (4 bytes). */
    private val lengthBitOffset = signatureLength * BITS_PER_BYTE

    /**
     * Calculates the available space for a hidden message in this image.
     *
     * Each pixel can store 3 bits (RGB), so total capacity is (width * height * 3) bits,
     * or (width * height * 3) / 8 bytes.
     */
    fun calculateHiddenSpaceInBytes(image: Image): Long =
        (image.width * image.height * 3L) / BITS_PER_BYTE - signatureLength - BYTE_COUNT_FOR_LENGTH - 1

    /**
     * Reads data hidden in the least significant bits of an image.
     *
     * Returns file name & bytes
     */
    fun readLeastSignificantBits(
        image: Image
    ): Pair<String, ByteArray>? {

        val bitmap = toBitmap(image)

        val pixelBytes = bitmap.readPixels()!!

        val actualSignature = readBytesFromBitOffset(
            pixelBytes = pixelBytes,
            bitOffset = 0,
            byteCount = signatureLength
        )

        if (!signatureBytes.contentEquals(actualSignature))
            return null

        /*
         * Check the length of the data hidden in the image.
         */

        val lengthBytes = readBytesFromBitOffset(
            pixelBytes = pixelBytes,
            bitOffset = lengthBitOffset,
            byteCount = BYTE_COUNT_FOR_LENGTH
        )

        val length = ((lengthBytes[0].toInt() and 0xFF) shl 24) or
            ((lengthBytes[1].toInt() and 0xFF) shl 16) or
            ((lengthBytes[2].toInt() and 0xFF) shl 8) or
            (lengthBytes[3].toInt() and 0xFF)

        /*
         * Validate the length of the data hidden in the image.
         */

        val maxDataLength = calculateHiddenSpaceInBytes(image)

        if (length <= 0 || length > maxDataLength)
            return null

        /*
         * Read the data hidden in the image.
         */

        val dataBitOffset = lengthBitOffset + 32

        val allData = readBytesFromBitOffset(
            pixelBytes = pixelBytes,
            bitOffset = dataBitOffset,
            byteCount = length
        )

        val indexOfTerminator = allData.indexOfFirst { it == TERMINATOR_BYTE }

        /* Without terminator the data stream is illegal */
        if (indexOfTerminator == -1)
            return null

        val fileName = allData.sliceArray(0 until indexOfTerminator).decodeToString()

        val data = allData.sliceArray(indexOfTerminator + 1 until allData.size)

        return fileName to data
    }

    /**
     * Reads a specified number of bytes starting from a given bit offset in the pixel data.
     */
    private fun readBytesFromBitOffset(
        pixelBytes: ByteArray,
        bitOffset: Int,
        byteCount: Int
    ): ByteArray {

        val result = ByteArray(byteCount)

        var currentBit = bitOffset

        var byteIndex = 0
        var currentByte = 0
        var bitsRead = 0

        while (byteIndex < byteCount) {

            /*
             * Check if we have read all bits for the current byte.
             */
            if (currentBit >= pixelBytes.size / 4 * 3)
                break

            val pixelIndex = currentBit / 3
            val channelIndex = currentBit % 3

            val pixelStart = pixelIndex * 4

            /*
             * Read the byte with the pixel data.
             */
            val channelByte = when (channelIndex) {
                0 -> pixelBytes[pixelStart]     // Red
                1 -> pixelBytes[pixelStart + 1] // Green
                2 -> pixelBytes[pixelStart + 2] // Blue
                else -> error("Invalid channel index")
            }

            val bit = channelByte.toInt() and 0x01

            /*
             * Assemble byte with bits in correct order (LSB to MSB as written).
             */
            currentByte = (currentByte shr 1) or (bit shl 7)

            bitsRead++

            if (bitsRead == BITS_PER_BYTE) {

                result[byteIndex] = currentByte.toByte()

                byteIndex++
                currentByte = 0
                bitsRead = 0
            }

            currentBit++
        }

        /* Handle partial byte if any bits were read */
        if (bitsRead > 0) {

            currentByte = currentByte shr (BITS_PER_BYTE - bitsRead)

            result[byteIndex] = currentByte.toByte()
        }

        return result
    }

    /**
     * Writes data to the least significant bits of an image.
     */
    fun writeLeastSignificantBits(
        image: Image,
        fileName: String,
        byteArray: ByteArray
    ): Image {

        /*
         * Write filename bytes
         */

        val filenameBytes = fileName.encodeToByteArray()

        /*
         * Write the length of the data to be hidden to a byte array.
         */

        val payloadLength = filenameBytes.size + 1 + byteArray.size

        val lengthBytes = ByteArray(BYTE_COUNT_FOR_LENGTH)

        lengthBytes[0] = ((payloadLength shr 24) and 0xFF).toByte()
        lengthBytes[1] = ((payloadLength shr 16) and 0xFF).toByte()
        lengthBytes[2] = ((payloadLength shr 8) and 0xFF).toByte()
        lengthBytes[3] = (payloadLength and 0xFF).toByte()

        /*
         * Prepare the data to be hidden:
         * Signature (9 bytes) + Length (4 bytes) + Filename (n bytes) + NUL + Data (n bytes) + NUL
         */
        val dataToWrite = signatureBytes + lengthBytes +
            filenameBytes + terminatorBytes + byteArray + terminatorBytes

        /*
         * Check if there is enough space for the data to be hidden.
         */

        val availableSpace = calculateHiddenSpaceInBytes(image)

        if (dataToWrite.size > availableSpace)
            error("Not enough space in the image for the given byte array: ${byteArray.size} > $availableSpace")

        /*
         * Write the data to the least significant bits of the image.
         */

        val bitmap = toBitmap(image)

        val pixelBytes = bitmap.readPixels()
            ?: error("Failed to read pixel bytes from bitmap")

        var dataIndex = 0
        var bitIndex = 0

        for (index in 0 until pixelBytes.size step 4) {

            /*
             * Check if we have read all data bytes.
             */
            if (dataIndex >= dataToWrite.size)
                break

            val red = pixelBytes[index]
            val green = pixelBytes[index + 1]
            val blue = pixelBytes[index + 2]

            /*
             * Red channel
             */

            val currentByte = dataToWrite[dataIndex].toInt() and 0xFF

            val redBit = (currentByte shr bitIndex) and 0x01

            pixelBytes[index] = (red.toInt() and 0xFE or redBit).toByte()

            bitIndex++

            if (bitIndex == BITS_PER_BYTE) {

                dataIndex++
                bitIndex = 0

                if (dataIndex >= dataToWrite.size)
                    break
            }

            /*
             * Green channel
             */

            val currentByteGreen = dataToWrite[dataIndex].toInt() and 0xFF

            val greenBit = (currentByteGreen shr bitIndex) and 0x01

            pixelBytes[index + 1] = (green.toInt() and 0xFE or greenBit).toByte()

            bitIndex++

            if (bitIndex == BITS_PER_BYTE) {

                dataIndex++
                bitIndex = 0

                if (dataIndex >= dataToWrite.size)
                    break
            }

            /*
             * Blue channel
             */

            val currentByteBlue = dataToWrite[dataIndex].toInt() and 0xFF

            val blueBit = (currentByteBlue shr bitIndex) and 0x01

            pixelBytes[index + 2] = (blue.toInt() and 0xFE or blueBit).toByte()

            bitIndex++

            if (bitIndex == BITS_PER_BYTE) {

                dataIndex++
                bitIndex = 0
            }
        }

        /*
         * Update the bitmap with the new pixel data.
         */
        bitmap.installPixels(pixelBytes)

        /*
         * Create a new Image from the bitmap.
         */
        return Image.Companion.makeFromBitmap(bitmap)
    }

    private fun toBitmap(image: Image): Bitmap {

        val bitmap = Bitmap().apply {
            allocPixels(
                imageInfo = ImageInfo.Companion.makeN32(
                    width = image.width,
                    height = image.height,
                    alphaType = ColorAlphaType.UNPREMUL
                )
            )
        }

        image.readPixels(bitmap, 0, 0)

        return bitmap
    }
}
