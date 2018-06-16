package com.andrew.socialactionssample.presentation.feature.base.di

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
abstract class AppCompatActivityModule <in Activity : AppCompatActivity> {

    @Provides
    fun provideAppCompatActivity(activity: Activity): AppCompatActivity = activity
}