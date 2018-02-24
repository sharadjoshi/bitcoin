package com.sharadjoshi.mine.bitcoin.application.di;

import android.app.Application;

import com.sharadjoshi.mine.bitcoin.application.BitcoinApplication;
import com.sharadjoshi.mine.bitcoin.main.di.ActivityBuilderModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = ActivityBuilderModule.class)
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(BitcoinApplication sproutApplication);
}