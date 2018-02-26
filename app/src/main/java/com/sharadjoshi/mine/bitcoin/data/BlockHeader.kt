package com.sharadjoshi.mine.bitcoin.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BlockHeader(val version : Int = 0) {
    val prevBlockhash: String = ""
    val merkleRoot: String = ""
    val timestamp: Long = 0
    val nBits: Long = 0
    var nonce: Long = 0
}