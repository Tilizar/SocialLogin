package com.andrew.socialactionssample

import com.andrew.socialactionssample.di.DaggerApplicationComponent
import com.vk.sdk.VKSdk
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by Andrew on 16.06.2018.
 */

class SocialActionsApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder().create(this)
}