package com.sharadjoshi.mine.bitcoin.network

import com.sharadjoshi.mine.bitcoin.data.Job
import retrofit2.Call
import retrofit2.http.GET

interface BlockServiceAPI {
    @GET("work/")
    fun getJob(): Call<Job>
}