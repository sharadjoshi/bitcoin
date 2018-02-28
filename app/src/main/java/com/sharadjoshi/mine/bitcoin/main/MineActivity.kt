package com.sharadjoshi.mine.bitcoin.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sharadjoshi.mine.bitcoin.R
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.main.viewmodel.BlockHeaderViewModel
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

        getBlock.setOnClickListener {
            blockHeaderViewModel.getBlock()
            processBlock.isEnabled = true
        }

        blockHeaderViewModel.blockHeader().observe(this, Observer { header -> block_details.setup(header ?: BlockHeader())})
        blockHeaderViewModel.nonce().observe(this, Observer {
            nonce -> progress.text = getString(R.string.processing_with_nonce, nonce) })
        blockHeaderViewModel.result().observe(this, Observer {
            result ->  progress.text = getString(R.string.final_hash, result)})

        processBlock.setOnClickListener {
            // Toggle processing
            if (blockHeaderViewModel.stop) {
                // if stopped already, start processing
                getBlock.isEnabled = false
                submitBlock.isEnabled = true
                processBlock.text = getString(R.string.stop_processing)
                blockHeaderViewModel.processBlock()
            } else {
                // else stop processing
                processBlock.text = getString(R.string.process_block)
                getBlock.isEnabled = true
                submitBlock.isEnabled = false
                blockHeaderViewModel.processBlock()
            }
        }

        submitBlock.setOnClickListener {
            getBlock.isEnabled = true
        }
    }
}
