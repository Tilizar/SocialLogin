package com.andrew.socialactionssample

import android.content.Context
import android.support.multidex.MultiDex
import com.andrew.social.login.core.initializer.SocialLoginInitializer
import com.andrew.socialactionssample.di.DaggerApplicationComponent
import com.andrew.socialactionssample.presentation.feature.customWebLogin.CustomWebViewLoginActivity
import com.andrew.social.login.core.web.WebActivityStarter
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject


/**
 * Created by Andrew on 16.06.2018.
 */

class SocialActionsApp : DaggerApplication() {

    @Inject
    lateinit var socialInit: SocialLoginInitializer

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        WebActivityStarter.setWebLoginActivity(CustomWebViewLoginActivity::class.java)
        socialInit.initAll(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder().create(this)
}