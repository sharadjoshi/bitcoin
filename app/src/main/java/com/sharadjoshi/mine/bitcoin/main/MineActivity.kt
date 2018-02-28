package com.sharadjoshi.mine.bitcoin.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sharadjoshi.mine.bitcoin.R
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.blockprocessor.toLittleEndian
import com.sharadjoshi.mine.bitcoin.main.viewmodel.BlockHeaderViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MineActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var blockHeaderViewModel: BlockHeaderViewModel

    private var blockHash = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        blockHeaderViewModel = ViewModelProviders.of(this, viewModelFactory).get(BlockHeaderViewModel::class.java)

        setContentView(R.layout.activity_main)

        with(blockHeaderViewModel) {
            blockHeader().observe(this@MineActivity, Observer { header ->
                block_details.setup(header ?: BlockHeader())
                blockHash = header?.blockHash ?: ""
                verify_result_final.text = ""
            })

            nonce().observe(this@MineActivity, Observer { nonce ->
                result_hash.text = getString(R.string.processing_with_nonce, nonce)
            })

            result().observe(this@MineActivity, Observer { result ->
                result_hash.text = getString(R.string.final_hash, result?.toLittleEndian())
                updateButtonsAsNotProcessing()

                with(verify_result_final) {
                    if (result?.toLittleEndian() == blockHash) { // if it matches the block hash
                        text = context.getString(R.string.verified)
                        setTextColor(Color.parseColor("#008000"))
                    } else {
                        text = context.getString(R.string.not_verified)
                        setTextColor(Color.parseColor("#8B0000"))
                    }
                }
            })

            getBlock.setOnClickListener {
                getBlock()
                processBlock.isEnabled = true
            }

            processBlock.setOnClickListener {
                updateButtonsAsProcessing()
                processBlock()
            }

            modifyBlock.setOnClickListener {
                // Increment the nonce and calculate the has again
                updateNonce()

                updateButtonsAsProcessing()
                processBlock()
            }
        }

        submitBlock.setOnClickListener {
            blockHeaderViewModel.sendResult(blockHash)
        }
    }

    private fun updateButtonsAsProcessing() {
        getBlock.isEnabled = false
        submitBlock.isEnabled = false
        processBlock.text = getString(R.string.stop_processing)
        modifyBlock.isEnabled = false
    }

    private fun updateButtonsAsNotProcessing() {
        processBlock.text = getString(R.string.verify_block)
        getBlock.isEnabled = true
        submitBlock.isEnabled = true
        modifyBlock.isEnabled = true
    }
}
