package com.sharadjoshi.mine.bitcoin.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sharadjoshi.mine.bitcoin.R
import dagger.android.AndroidInjection

class MineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
