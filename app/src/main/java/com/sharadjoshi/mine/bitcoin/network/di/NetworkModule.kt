package com.sharadjoshi.mine.bitcoin.network.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient)
                .baseUrl("http://ec2-18-220-199-84.us-east-2.compute.amazonaws.com/")
                .build()
    }

    @Provides
    fun provideOkHttpclient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    fun provideLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.i(message) })
    }
}
