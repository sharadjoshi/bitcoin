package com.sharadjoshi.mine.bitcoin.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sharadjoshi.mine.bitcoin.blockprocessor.BlockHandler
import com.sharadjoshi.mine.bitcoin.blockprocessor.HashCashGenerator
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.data.toHexString
import com.sharadjoshi.mine.bitcoin.network.BlockService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BlockHeaderViewModel @Inject constructor(
        private val blockService: BlockService,
        private val hashCashGenerator: HashCashGenerator) : ViewModel(), BlockHandler {

    private var newBlockHeader : BlockHeader = BlockHeader() // backing data for the mutable live data
    private var blockHeader : MutableLiveData<BlockHeader> = MutableLiveData()
    private var resultHash : MutableLiveData<String> = MutableLiveData()
    private var nonce : MutableLiveData<Int> = MutableLiveData()
    var stop : Boolean = false

    fun getBlock() {
        blockService.getJob(this)
    }

    fun blockHeader() : LiveData<BlockHeader> {
        return blockHeader
    }

    fun nonce() : LiveData<Int> {
        return nonce
    }

    fun result() : LiveData<String> {
        return resultHash
    }

    fun processBlock() {
        stop = !stop

        if (!stop) {
            getHashGeneratorSingle(newBlockHeader)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result -> resultHash.value = result }
        }
    }

    // Handles arrival of a new block
    override fun handleBlock(blockHeader: BlockHeader) {
        newBlockHeader = blockHeader
        this.blockHeader.value = newBlockHeader
        this.nonce.value = newBlockHeader.nonce
    }

    private fun getHashGeneratorSingle(blockHeader: BlockHeader): Single<String> {
        return Single.fromCallable {
            var blockHeaderCopy = newBlockHeader
            var blockMined = true
            var newHash = ByteArray(32)

            do {
                newHash = hashCashGenerator.generateHash(blockHeader)
//                blockMined = newHash.isSmaller(target)
//                blockHeaderCopy.nonce++
            } while (!stop && !blockMined)
            newHash
        }.map { it.toHexString() }

    }
}