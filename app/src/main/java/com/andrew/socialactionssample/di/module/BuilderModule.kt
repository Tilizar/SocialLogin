package com.andrew.socialactionssample.di.module

import com.andrew.socialactionssample.di.PerActivity
import com.andrew.socialactionssample.presentation.feature.main.di.MainActivityModule
import com.andrew.socialactionssample.presentation.feature.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by Andrew on 16.06.2018.
 */

@Module(includes = [AndroidSupportInjectionModule::class])
interface BuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class, SocialModule::class])
    fun provideMainActivity(): MainActivity
}