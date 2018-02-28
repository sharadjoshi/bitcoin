package com.sharadjoshi.mine.bitcoin.network

import com.sharadjoshi.mine.bitcoin.data.Job
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface BlockServiceAPI {
    @GET("work/")
    fun getJob(): Call<Job>

    // Returns <Job> for simplicity
    @POST("/submit")
    @FormUrlEncoded
    fun postResult(@Field("resultHash") result: String) : Call<Job>
}