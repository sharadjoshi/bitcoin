package com.sharadjoshi.mine.bitcoin.blockprocessor.di

import com.sharadjoshi.mine.bitcoin.blockprocessor.HashCashGenerator
import dagger.Module
import dagger.Provides
import java.security.MessageDigest

@Module(includes = [(MessageDigestModule::class)])
class HashCashGeneratorModule {
    @Provides
    fun provideHashCashGenerator(messageDigest: MessageDigest) : HashCashGenerator {
        return HashCashGenerator(messageDigest)
    }
}
