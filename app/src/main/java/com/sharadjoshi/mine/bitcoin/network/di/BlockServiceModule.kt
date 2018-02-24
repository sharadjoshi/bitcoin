package com.sharadjoshi.mine.bitcoin.network.di

import com.sharadjoshi.mine.bitcoin.network.BlockService

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = arrayOf(NetworkModule::class))
class BlockServiceModule {
    @Provides
    fun provideBlockService(retrofit: Retrofit): BlockService {
        return retrofit.create(BlockService::class.java)
    }
}
