package com.andrew.socialactionssample.presentation.feature.main.di

import android.support.v7.widget.LinearLayoutManager
import com.andrew.socialactionssample.di.PerActivity
import com.andrew.socialactionssample.presentation.feature.base.di.AppCompatActivityModule
import com.andrew.socialactionssample.presentation.feature.main.adapter.SocialsAdapter
import com.andrew.socialactionssample.presentation.feature.main.view.MainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
class MainActivityModule : AppCompatActivityModule<MainActivity>() {

    @PerActivity
    @Provides
    fun provideAdapter(activity: MainActivity) = SocialsAdapter(activity)

    @PerActivity
    @Provides
    fun provideLayoutManager(activity: MainActivity) = LinearLayoutManager(activity)
}