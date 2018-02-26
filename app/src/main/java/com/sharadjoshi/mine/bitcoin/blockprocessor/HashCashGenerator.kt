package com.sharadjoshi.mine.bitcoin.blockprocessor

import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import timber.log.Timber
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import javax.inject.Inject

class HashCashGenerator @Inject constructor(private val messageDigest: MessageDigest) {
    fun currentHash(blockHeader: BlockHeader) : ByteArray {
        return stringToHex(blockHeader.prevBlockhash)
    }

    fun generateHash(blockHeader: BlockHeader) : ByteArray {
        val headerBigEndian : ByteBuffer = ByteBuffer.allocate(80)

        // BitCoin hashcash is (hash of (hash of (block header in device endian format))
        // The hash function is SHA256
        headerBigEndian.order(ByteOrder.LITTLE_ENDIAN)

        with(headerBigEndian) {
            putInt(blockHeader.version)                      // 4 bytes - version
            put(stringToHex(blockHeader.prevBlockhash))      // 32 bytes - previous block hash
            put(stringToHex(blockHeader.merkleRoot))         // 32 bytes - merkel root
            putInt(System.currentTimeMillis().toInt())       // 4 bytes - start time of mining
            putInt(blockHeader.nBits.toInt())                // 4 bytes - target bits
            putInt(blockHeader.nonce.toInt())          // 4 bytes - linearly increasing nonce
        }

        val blockHash = messageDigest.digest(messageDigest.digest(headerBigEndian.array()))
        Timber.v("hash- $blockHash")
        return blockHash
    }

    fun isHashSmaller(newHash: ByteArray, oldHash: ByteArray) : Boolean {
        for (i in 0..31) {
            if (newHash[i] != oldHash[i]) {
                Timber.v("not equal - new ${newHash[i]}, old ${oldHash[i]}, ${newHash[i] < oldHash[i]}")
                return newHash[i] < oldHash[i]
            }
        }

        return false
    }

    // Check if the hash is smaller than the target
    fun isHashSmaller2(newHash: ByteArray, oldHash: ByteArray) : Boolean {
        val newHahZeros = (0..31)
                .takeWhile { newHash[it].toInt() == 0 }
                .count()

        val oldHahZeros = (0..31)
                .takeWhile { oldHash[it].toInt() == 0 }
                .count()

        return newHahZeros > oldHahZeros
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
