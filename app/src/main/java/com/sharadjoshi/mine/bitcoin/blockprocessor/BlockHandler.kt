package com.sharadjoshi.mine.bitcoin.blockprocessor

import com.sharadjoshi.mine.bitcoin.data.BlockHeader

interface BlockHandler {
    fun handleBlock(blockHeader: BlockHeader)
}