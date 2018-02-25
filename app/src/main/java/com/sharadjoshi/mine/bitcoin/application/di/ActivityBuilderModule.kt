package com.sharadjoshi.mine.bitcoin.application.di

import com.sharadjoshi.mine.bitcoin.main.MineActivity
import com.sharadjoshi.mine.bitcoin.main.di.MineActivityModule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = arrayOf(AndroidSupportInjectionModule::class))
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MineActivityModule::class))
    internal abstract fun MineActivityInjector(): MineActivity
}
