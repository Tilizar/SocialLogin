package com.andrew.socialactionssample

import android.content.Context
import android.support.multidex.MultiDex
import com.andrew.socialactionssample.di.DaggerApplicationComponent
import com.facebook.appevents.AppEventsLogger
import com.twitter.sdk.android.core.Twitter
import com.vk.sdk.VKSdk
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


/**
 * Created by Andrew on 16.06.2018.
 */

class SocialActionsApp : DaggerApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
        Twitter.initialize(this)
        AppEventsLogger.activateApp(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder().create(this)
}