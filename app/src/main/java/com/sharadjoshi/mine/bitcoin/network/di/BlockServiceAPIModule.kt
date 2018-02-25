package com.sharadjoshi.mine.bitcoin.network.di


import com.sharadjoshi.mine.bitcoin.network.BlockService
import com.sharadjoshi.mine.bitcoin.network.BlockServiceAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [(NetworkModule::class)])
class BlockServiceAPIModule {
    @Provides
    fun provideBlockService(blockServiceAPI: BlockServiceAPI): BlockService {
        return BlockService(blockServiceAPI)
    }

    @Provides
    fun provideBlockServiceAPI(retrofit: Retrofit) : BlockServiceAPI {
        return retrofit.create(BlockServiceAPI::class.java)
    }
}
