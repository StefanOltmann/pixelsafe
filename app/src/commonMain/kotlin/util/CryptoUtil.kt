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

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.algorithms.SHA256
import dev.whyoleg.cryptography.operations.Hasher

/**
 * Utility for encryption and decryption operations using AES-GCM.
 *
 * This object provides functionality to encrypt and decrypt data using the AES-GCM algorithm.
 *
 * See https://whyoleg.github.io/cryptography-kotlin/examples/#aes-gcm
 */
object CryptoUtil {

    val keyFormat = AES.Key.Format.RAW

    private val provider = CryptographyProvider.Default

    private val hasher: Hasher = provider.get(SHA256).hasher()

    private val keyDecoder = provider.get(AES.GCM).keyDecoder()

    /**
     * Derives a 32-byte key from the password using SHA-256.
     *
     * @param password The password to derive the key from
     * @return A 32-byte key suitable for AES-256
     */
    suspend fun deriveKey(password: String?): AES.GCM.Key? =
        if (!password.isNullOrBlank())
            keyDecoder.decodeFromByteArray(
                format = keyFormat,
                bytes = hasher.hash(password.encodeToByteArray())
            )
        else
            null

    /**
     * Encrypts the provided byte array using the specified AES GCM key if the key is not null.
     * If the key is null, the method returns the original byte array without any encryption.
     *
     * @param bytes The plaintext byte array that may need encryption.
     * @param key The optional AES GCM key to use for encryption. If null, encryption is skipped.
     * @return A byte array containing either the encrypted data or the original plaintext if no encryption is performed.
     */
    suspend fun encryptIfNeeded(
        bytes: ByteArray,
        key: AES.GCM.Key?
    ): ByteArray {

        if (key == null)
            return bytes

        return encrypt(bytes, key)
    }

    /**
     * Encrypts the given byte array using the specified AES GCM key.
     *
     * @param bytes The plaintext byte array to be encrypted.
     * @param key The AES GCM key to use for encryption.
     * @return A byte array containing the encrypted data.
     */
    @OptIn(ExperimentalStdlibApi::class)
    suspend fun encrypt(
        bytes: ByteArray,
        key: AES.GCM.Key
    ): ByteArray {

        val cipher = key.cipher()

        val encryptedBytes: ByteArray = cipher.encrypt(plaintext = bytes)

        return encryptedBytes
    }

    /**
     * Decrypts the provided encrypted byte array if a valid AES-GCM key is provided.
     * If the key is null, the method returns the original byte array.
     *
     * @param encryptedBytes The encrypted byte array to be processed.
     * @param key The AES-GCM key used for decryption. If null, no decryption is performed.
     * @return The decrypted byte array if a valid key is provided, otherwise the original encrypted byte array.
     */
    suspend fun decryptIfNeeded(
        encryptedBytes: ByteArray,
        key: AES.GCM.Key?
    ): ByteArray {

        if (key == null)
            return encryptedBytes

        return decrypt(encryptedBytes, key)
    }

    /**
     * Decrypts the provided encrypted byte array using the specified AES-GCM key.
     *
     * @param encryptedBytes The byte array representing the encrypted data.
     * @param key The AES-GCM key to be used for decryption.
     * @return The decrypted byte array.
     * @throws DecryptionException If decryption fails due to an invalid key or corrupted data.
     */
    suspend fun decrypt(
        encryptedBytes: ByteArray,
        key: AES.GCM.Key
    ): ByteArray {

        try {

            val cipher = key.cipher()

            val bytes: ByteArray = cipher.decrypt(ciphertext = encryptedBytes)

            return bytes

        } catch (_: Throwable) {


            /*
             * Decryption can fail if the key is wrong.
             * We get different exceptions on the platforms,
             * so we throw a custom common one.
             */
            throw DecryptionException()
        }
    }
}
