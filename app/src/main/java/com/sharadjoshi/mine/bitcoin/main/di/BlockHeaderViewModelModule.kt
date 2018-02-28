package com.sharadjoshi.mine.bitcoin.main.di

import android.arch.lifecycle.ViewModel
import com.sharadjoshi.mine.bitcoin.application.di.ViewModelKey
import com.sharadjoshi.mine.bitcoin.blockprocessor.di.HashCashGeneratorModule
import com.sharadjoshi.mine.bitcoin.network.di.BlockServiceAPIModule
import com.sharadjoshi.mine.bitcoin.main.viewmodel.BlockHeaderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = arrayOf(HashCashGeneratorModule::class, BlockServiceAPIModule::class))
abstract class BlockHeaderViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(BlockHeaderViewModel::class)
    abstract fun bindBlockHeaderViewModel(blockHeaderViewModel: BlockHeaderViewModel): ViewModel
}