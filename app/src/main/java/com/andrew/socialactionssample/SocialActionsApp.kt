package com.andrew.socialactionssample

import android.content.Context
import android.support.multidex.MultiDex
import com.andrew.social.login.core.initializer.SocialLoginInitializer
import com.andrew.socialactionssample.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.util.*
import javax.inject.Inject
import kotlin.Comparator
import kotlin.collections.ArrayList


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
        socialInit.initAll(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder().create(this)
}