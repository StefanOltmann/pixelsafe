package util

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.fail

class CryptoUtilTest {

    private val testPassword: String = "SuperSecret123"

    private val testBytes = "This is a secret message.".encodeToByteArray()

    @OptIn(ExperimentalStdlibApi::class)
    private val testBytesHexString = testBytes.toHexString()

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testDeriveKey(): Unit = runBlocking {

        val key = CryptoUtil.deriveKey(testPassword) ?: fail("Key is null.")

        assertEquals(
            expected = "343fcb40497549085c98ae137c137116a5c2442eb8dc0bf0cac3a3419ce05b9f",
            actual = key.encodeToByteArray(CryptoUtil.keyFormat).toHexString()
        )

        /*
         * For comparison with the original.
         */
        assertEquals(
            expected = "5375706572536563726574313233",
            actual = testPassword.encodeToByteArray().toHexString()
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testEncryptAndTryDecrypt(): Unit = runBlocking {

        val key = CryptoUtil.deriveKey(testPassword) ?: fail("Key is null.")

        val encryptedBytes = CryptoUtil.encrypt(testBytes, key)

        val decryptedBytes = CryptoUtil.tryDecrypt(encryptedBytes, key)

        /*
         * Should return the same message.
         */
        assertContentEquals(
            expected = testBytes,
            actual = decryptedBytes
        )

        /*
         * For security
         */
        assertEquals(
            expected = testBytesHexString,
            actual = decryptedBytes.toHexString()
        )
    }
}
