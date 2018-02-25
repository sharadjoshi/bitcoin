package com.sharadjoshi.mine.bitcoin.application.di

import android.arch.lifecycle.ViewModelProvider
import com.sharadjoshi.mine.bitcoin.viewmodel.di.BlockHeaderViewModelModule
import dagger.Binds
import dagger.Module

@Module(includes = [(BlockHeaderViewModelModule::class)])
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}