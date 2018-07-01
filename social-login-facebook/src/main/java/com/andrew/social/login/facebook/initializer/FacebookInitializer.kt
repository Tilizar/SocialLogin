package com.andrew.social.login.facebook.initializer

import android.app.Application
import com.andrew.social.login.base.initializer.Initializer
import com.facebook.appevents.AppEventsLogger

/**
 * Created by Andrew on 01.07.2018
 */

class FacebookInitializer : Initializer {

    override fun init(app: Application) {
        AppEventsLogger.activateApp(app)
    }
}