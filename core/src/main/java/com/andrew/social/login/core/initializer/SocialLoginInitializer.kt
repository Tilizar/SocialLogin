package com.andrew.social.login.core.initializer

import android.app.Application
import com.andrew.social.login.core.SocialType

/**
 * Created by Andrew on 01.07.2018
 */

class SocialLoginInitializer(private val socialsToInit: Map<SocialType, Initializer>) {

    fun init(socialType: SocialType, app: Application) {
        socialsToInit[socialType]?.init(app)
    }

    fun initAll(app: Application) {
        socialsToInit.forEach { it.value.init(app) }
    }
}