package com.sharadjoshi.mine.bitcoin.blockprocessor

import com.sharadjoshi.mine.bitcoin.data.BlockHeader

interface BlockProcessor {
    fun processBlock(blockHeader: BlockHeader)
}