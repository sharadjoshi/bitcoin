package com.sharadjoshi.mine.bitcoin.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class BlockHeader(
        @JsonProperty("version") val version : Int = 0,
        @JsonProperty("miner") val miner : String = "Unknown",
        @JsonProperty("height") val blockId : Int = 0,
        @JsonProperty("previous_block_hash") val prevBlockhash : String = "",
        @JsonProperty("merkelroot") val merkleroot: String? = "",
        @JsonProperty("hash") val blockHash : String = "",
        @JsonProperty("timestamp") val timestamp : Int = 0,
        @JsonProperty("bits") val target : Int = 0,
        @JsonProperty("nonce") var nonce : Long = 0)