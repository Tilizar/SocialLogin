package com.andrew.socialactionssample.presentation.feature.main.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.andrew.socialactionssample.di.PerActivity
import com.andrew.socialactionssample.presentation.feature.base.di.ActivityModule
import com.andrew.socialactionssample.presentation.feature.main.adapter.SocialsAdapter
import com.andrew.socialactionssample.presentation.feature.main.view.MainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
class MainActivityModule : ActivityModule<MainActivity>() {

    @PerActivity
    @Provides
    fun provideAdapter(activity: MainActivity): SocialsAdapter = SocialsAdapter(activity)

    @PerActivity
    @Provides
    fun provideLayoutManager(activity: MainActivity): LinearLayoutManager = LinearLayoutManager(activity)
}