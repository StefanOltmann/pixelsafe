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

import kotlinx.coroutines.runBlocking
import org.jetbrains.skia.Image
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StenographyUtilTest {

    private val fileName = "my_secret_message.txt"

    private val testPassword: String = "TheBestPasswordEver1337"

    private val hiddenTestBytesInTestPng = """
        Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy
        eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
        voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
        clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
        Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy
        eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
        voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
        Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
    """.trimIndent().encodeToByteArray()

    private val hiddenTestBytesForRoundTrip = """
        Weit hinten, hinter den Wortbergen, fern der Länder Vokalien und Konsonantien
        leben die Blindtexte. Abgeschieden wohnen sie in Buchstabhausen an der Küste
        des Semantik, eines großen Sprachozeans. Ein kleines Bächlein namens Duden
        fließt durch ihren Ort und versorgt sie mit den nötigen Regelialien.
        Es ist ein paradiesmatisches Land, in dem einem gebratene Satzteile in den Mund fliegen.
        Nicht einmal von der allmächtigen Interpunktion werden die Blindtexte beherrscht –
        ein geradezu unorthographisches Leben.
    """.trimIndent().encodeToByteArray()

    @Test
    fun testCalculateHiddenSpaceInBytes() {

        val testBytes = readTestBytes("test.png")

        val image = Image.Companion.makeFromEncoded(testBytes)

        val hiddenSpace = SteganographyUtil.calculateApproximateHiddenSpaceInBytes(image)

        assertEquals(
            expected = 24534L,
            actual = hiddenSpace
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testReadLeastSignificantBits() = runBlocking {

        val testBytes = readTestBytes("test.png")

        val image = Image.Companion.makeFromEncoded(testBytes)

        val result = SteganographyUtil.readLeastSignificantBits(image)

        assertNotNull(result)

        assertEquals(
            expected = fileName,
            actual = result.first
        )

        assertContentEquals(
            expected = hiddenTestBytesInTestPng,
            actual = result.second
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testWriteLeastSignificantBits() = runBlocking {

        val testBytes = readTestBytes("test.png")

        val image = Image.Companion.makeFromEncoded(testBytes)

        val resultingImage = SteganographyUtil.writeLeastSignificantBits(
            image = image,
            fileName = fileName,
            bytes = hiddenTestBytesForRoundTrip
        )

        val actualBytes = resultingImage.encodeToData()!!.bytes

        val expectedBytes = readTestBytes("test_mod.png")

        assertContentEquals(
            expected = expectedBytes,
            actual = actualBytes
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testReadAndWriteLeastSignificantBits() = runBlocking {

        val testBytes = readTestBytes("test.png")

        val image = Image.Companion.makeFromEncoded(testBytes)

        assertNotNull(image)

        val resultingImage = SteganographyUtil.writeLeastSignificantBits(
            image = image,
            fileName = fileName,
            bytes = hiddenTestBytesForRoundTrip
        )

        assertNotNull(resultingImage)

        val result = SteganographyUtil.readLeastSignificantBits(resultingImage)

        assertNotNull(result)

        assertEquals(
            expected = fileName,
            actual = result.first
        )

        assertContentEquals(
            expected = hiddenTestBytesForRoundTrip,
            actual = result.second
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testReadAndWriteLeastSignificantBitsWithPassword() = runBlocking {

        val testBytes = readTestBytes("test.png")

        val image = Image.Companion.makeFromEncoded(testBytes)

        assertNotNull(image)

        val resultingImage = SteganographyUtil.writeLeastSignificantBits(
            image = image,
            fileName = fileName,
            bytes = hiddenTestBytesForRoundTrip,
            password = testPassword
        )

        assertNotNull(resultingImage)

        val result = SteganographyUtil.readLeastSignificantBits(
            image = resultingImage,
            password = testPassword
        )

        assertNotNull(result)

        assertEquals(
            expected = fileName,
            actual = result.first
        )

        assertContentEquals(
            expected = hiddenTestBytesForRoundTrip,
            actual = result.second
        )
    }

    private fun readTestBytes(fileName: String): ByteArray =
        this::class.java.getResourceAsStream(fileName)!!.readBytes()
}
