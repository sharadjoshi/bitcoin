package com.sharadjoshi.mine.bitcoin.network

import com.sharadjoshi.mine.bitcoin.data.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class BlockService(var blockServiceAPI: BlockServiceAPI) : Callback<Job> {

    fun getJob() {
        val j = blockServiceAPI.getJob()
        j.enqueue(this)
    }

    override fun onResponse(call: Call<Job>?, response: Response<Job>?) {
        val j: Job? = response?.body()

        Timber.v("Received ${j.toString()}")
    }

    override fun onFailure(call: Call<Job>?, t: Throwable?) {
        Timber.e("Failed to receive job")
    }
}
