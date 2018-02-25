package com.sharadjoshi.mine.bitcoin.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sharadjoshi.mine.bitcoin.R
import com.sharadjoshi.mine.bitcoin.network.BlockService
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MineActivity : AppCompatActivity() {
    @Inject lateinit var blockService: BlockService

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        get_block.setOnClickListener({ blockService.getJob()} )
    }
}
