package com.sharadjoshi.mine.bitcoin.blockprocessor.di

import dagger.Module
import dagger.Provides
import java.security.MessageDigest

@Module
class MessageDigestModule {
    @Provides
    fun provideMessageDigest(): MessageDigest {
        return MessageDigest.getInstance("SHA-256")
    }
}