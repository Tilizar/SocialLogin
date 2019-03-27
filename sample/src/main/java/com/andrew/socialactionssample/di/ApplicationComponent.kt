package com.andrew.socialactionssample.di

import com.andrew.socialactionssample.SocialActionsApp
import com.andrew.socialactionssample.di.module.AppModule
import com.andrew.socialactionssample.di.module.BuilderModule
import com.andrew.socialactionssample.di.module.SocialInitModule
import dagger.Component
import dagger.android.AndroidInjector

/**
 * Created by Andrew on 16.06.2018.
 */

@PerApplication
@Component(modules = [
    AppModule::class,
    BuilderModule::class,
    SocialInitModule::class
])
interface ApplicationComponent : AndroidInjector<SocialActionsApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SocialActionsApp>()
}