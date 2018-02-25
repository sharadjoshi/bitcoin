package com.sharadjoshi.mine.bitcoin.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sharadjoshi.mine.bitcoin.R
import com.sharadjoshi.mine.bitcoin.blockprocessor.BlockProcessor
import com.sharadjoshi.mine.bitcoin.blockprocessor.HashCashGenerator
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.network.BlockService
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MineActivity : AppCompatActivity(), BlockProcessor {
    @Inject lateinit var blockService: BlockService
    @Inject lateinit var hashCashGenerator: HashCashGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        var blockHeader: BlockHeader = BlockHeader()

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        get_block.setOnClickListener({ blockService.getJob(this)} )
        process_block.setOnClickListener({})
    }

    override fun processBlock(blockHeader: BlockHeader) {
        hashCashGenerator.generateHash(blockHeader)
    }
}
