package com.andrew.socialactionssample.presentation.feature.base.di

import android.app.Activity
import dagger.Module
import dagger.Provides

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
abstract class ActivityModule <in CurrentActivity : Activity> {

    @Provides
    fun provideActivity(activity: CurrentActivity): Activity = activity
}