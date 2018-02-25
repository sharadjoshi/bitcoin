package com.sharadjoshi.mine.bitcoin.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sharadjoshi.mine.bitcoin.R
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.viewmodel.BlockHeaderViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MineActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var blockHeaderViewModel: BlockHeaderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        blockHeaderViewModel = ViewModelProviders.of(this, viewModelFactory).get(BlockHeaderViewModel::class.java)

        setContentView(R.layout.activity_main)

        get_block.setOnClickListener({ blockHeaderViewModel.getBlock() })

        blockHeaderViewModel.blockHeader.observe(this, Observer { header -> showHeader(header?: BlockHeader()) })
        blockHeaderViewModel.nonce.observe(this, Observer { nonce -> progress.text = nonce.toString() })

        process_block.setOnClickListener({ blockHeaderViewModel.processBlock() })
    }

    private fun showHeader(blockHeader: BlockHeader) {
        val sb = StringBuilder()
        sb.append("New Block\n")
                .append("Version       : ${blockHeader.version}\n")
                .append("Previous Hash : ${blockHeader.prevBlockhash}\n")
                .append("Merkel Root   : ${blockHeader.merkleRoot}\n")
                .append("Timestamp     : ${blockHeader.timestamp}\n")
                .append("Nonce         : ${blockHeader.nonce}")

        block_details.text = sb.toString()
    }
}
