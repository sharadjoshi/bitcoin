package com.sharadjoshi.mine.bitcoin.blockprocessor

import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import timber.log.Timber
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import javax.inject.Inject

class HashCashGenerator @Inject constructor(private val messageDigest: MessageDigest) {
    fun generateHash(blockHeader: BlockHeader) : ByteArray {
        val headerBigEndian : ByteBuffer = ByteBuffer.allocate(80)

        // BitCoin hashcash is (hash of (hash of (block header in big endian format))
        // The hash function is SHA256
        headerBigEndian.order(ByteOrder.BIG_ENDIAN)

        with(headerBigEndian) {
            putInt(blockHeader.version)                      // 4 bytes
            put(stringToHex(blockHeader.prevBlockhash))      // 32 bytes
            put(stringToHex(blockHeader.merkleRoot))         // 32 bytes
            putInt(blockHeader.timestamp.toInt())            // 4 bytes
            putInt(blockHeader.difficultyTarget.toInt())     // 4 bytes
            putInt(blockHeader.nonce.toInt())          // 4 bytes
        }

        val blockHash = messageDigest.digest(messageDigest.digest(headerBigEndian.array()))
        Timber.v("hash- $blockHash")
        return blockHash
    }

    fun compareHash(newHash: String, blockHeader: BlockHeader) : Int {
        return 0
    }

    // Converts 64 byte string representation of the hash
    // that we received in the json into a 32 byte byte-array hex value
    private fun stringToHex(hash: String) : ByteArray {
        val value = ByteArray(32) // 32 byte array

        for(i in 1..32) {
            val firstNibble   : Int = charToInt(hash[i*2-2]) shl 4
            val secondNibble  : Int = charToInt(hash[i*2-1])

            // Combine them into one byte
            value[i-1] = (firstNibble or secondNibble).toByte()
        }

        return value
    }

    // We need this method because the Kotlin Char.toInt() does a literal conversion
    // i.e. the value of char '0' will be 48 instead of 0
    private fun charToInt(char: Char) : Int {
        return if (char.isDigit()) (char - '0') else (char - 'a') + 10
    }
}
