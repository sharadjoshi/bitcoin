package com.sharadjoshi.mine.bitcoin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sharadjoshi.mine.bitcoin.blockprocessor.BlockHandler
import com.sharadjoshi.mine.bitcoin.blockprocessor.HashCashGenerator
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.network.BlockService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BlockHeaderViewModel @Inject constructor(
        private val blockService: BlockService,
        private val hashCashGenerator: HashCashGenerator) : ViewModel(), BlockHandler {

    private var newBlockHeader : BlockHeader = BlockHeader()
    var blockHeader : MutableLiveData<BlockHeader> = MutableLiveData()
    val nonce : MutableLiveData<Long> = MutableLiveData()
    var stop : Boolean = false

    fun getBlock() {
        blockService.getJob(this)
    }

    fun processBlock() {
        stop = !stop

        if (!stop) {
            getHashGeneratorSingle(newBlockHeader)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
    }

    // Handles arrival of a new block
    override fun handleBlock(blockHeader: BlockHeader) {
        newBlockHeader = blockHeader
        this.blockHeader.value = newBlockHeader
        this.nonce.value = newBlockHeader.nonce
    }

    private fun getHashGeneratorSingle(blockHeader: BlockHeader): Single<Unit> {
        return Single.fromCallable {
            val oldHash = hashCashGenerator.currentHash(newBlockHeader)
            var blockHeaderCopy = newBlockHeader
            var blockMined = false
            var newHash = ByteArray(32)

            do {
                newHash = hashCashGenerator.generateHash(blockHeader)
                blockMined = hashCashGenerator.isHashSmaller2(newHash, oldHash)

                blockHeaderCopy.nonce++
            } while (!stop && !blockMined)
        }
    }
}