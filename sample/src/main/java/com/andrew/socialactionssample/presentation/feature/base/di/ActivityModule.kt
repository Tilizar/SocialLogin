package com.andrew.socialactionssample.presentation.feature.base.di

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
abstract class ActivityModule <in CurrentActivity : FragmentActivity> {

    @Provides
    fun provideActivity(activity: CurrentActivity): FragmentActivity = activity
}