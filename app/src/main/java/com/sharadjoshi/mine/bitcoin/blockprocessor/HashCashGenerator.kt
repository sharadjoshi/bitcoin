package com.sharadjoshi.mine.bitcoin.blockprocessor

import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.data.toHexString
import com.sharadjoshi.mine.bitcoin.data.toHex
import timber.log.Timber
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import javax.inject.Inject

class HashCashGenerator @Inject constructor(private val messageDigest: MessageDigest) {

    fun generateHash(blockHeader: BlockHeader) : ByteArray {
        val headerLittleEndian : ByteBuffer = ByteBuffer.allocate(80)
        headerLittleEndian.order(ByteOrder.LITTLE_ENDIAN)

        // BitCoin hashcash is (hash of (hash of (block header in little endian format))
        // The hash function is SHA256. The generated has is also in little endian format.

        with(headerLittleEndian) {
            putInt(blockHeader.version)                      // 4 bytes - version
            put(blockHeader.prevBlockhash.toHex())           // 32 bytes - previous block hash
            put(blockHeader.merkleRoot.toHex())              // 32 bytes - merkel root
            putInt(blockHeader.timestamp)                    // 4 bytes - start time of mining
            put(blockHeader.target.toHex())                  // 4 bytes - target bits
            putInt(blockHeader.nonce)                  // 4 bytes - nonce
        }

        Timber.d("Block hex - ${headerLittleEndian.array().toHexString()}") // for debugging

        val blockHash = messageDigest.digest(messageDigest.digest(headerLittleEndian.array()))

        Timber.d("Generated hash - ${blockHash.toHexString()}") // for debugging
        return blockHash
    }
}
