package com.andrew.socialactionssample.di.module

import android.content.Context
import com.andrew.socialactionssample.SocialActionsApp
import com.andrew.socialactionssample.di.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
class AppModule {

    @PerApplication
    @Provides
    fun provideContext(app: SocialActionsApp) : Context = app
}