package com.sharadjoshi.mine.bitcoin.main.di

import android.app.Activity
import com.sharadjoshi.mine.bitcoin.application.di.ActivityScope
import com.sharadjoshi.mine.bitcoin.main.MineActivity
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [(AndroidSupportInjectionModule::class)])
abstract class MineActivityModule {
    @Binds
    @ActivityScope
    internal abstract fun activity(mineActivity: MineActivity): Activity
}
