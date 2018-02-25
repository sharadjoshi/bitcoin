package com.sharadjoshi.mine.bitcoin.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Job (val jobId: Int = 0, val clientId: Int = 0) {
    val blockHeader: BlockHeader = BlockHeader()
}