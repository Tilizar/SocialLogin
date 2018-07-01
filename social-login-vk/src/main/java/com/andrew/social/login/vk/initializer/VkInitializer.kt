package com.andrew.social.login.vk.initializer

import android.app.Application
import com.andrew.social.login.base.initializer.Initializer
import com.vk.sdk.VKSdk

/**
 * Created by Andrew on 01.07.2018
 */

class VkInitializer : Initializer {

    override fun init(app: Application) {
        VKSdk.initialize(app)
    }
}