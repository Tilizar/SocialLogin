package com.andrew.social.login.twitter.initializer

import android.app.Application
import com.andrew.social.login.base.initializer.Initializer
import com.twitter.sdk.android.core.Twitter

/**
 * Created by Andrew on 01.07.2018
 */

class TwitterInitializer : Initializer {

    override fun init(app: Application) {
        Twitter.initialize(app)
    }
}