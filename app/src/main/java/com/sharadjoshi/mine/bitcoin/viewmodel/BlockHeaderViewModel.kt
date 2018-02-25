package com.sharadjoshi.mine.bitcoin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sharadjoshi.mine.bitcoin.blockprocessor.BlockProcessor
import com.sharadjoshi.mine.bitcoin.blockprocessor.HashCashGenerator
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.network.BlockService
import javax.inject.Inject

class BlockHeaderViewModel @Inject constructor(
        private val blockService: BlockService,
        private val hashCashGenerator: HashCashGenerator) : ViewModel(), BlockProcessor {

    private var newBlockHeader : BlockHeader = BlockHeader()
    var blockHeader : MutableLiveData<BlockHeader> = MutableLiveData()
    val nonce : MutableLiveData<Long> = MutableLiveData()

    fun getBlock() {
        blockService.getJob(this)
    }

    fun processBlock() {
        hashCashGenerator.generateHash(newBlockHeader)
    }

    override fun processBlock(blockHeader: BlockHeader) {
        newBlockHeader = blockHeader
        this.blockHeader.value = newBlockHeader
        this.nonce.value = newBlockHeader.nonce
    }
}