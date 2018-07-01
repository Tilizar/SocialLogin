package com.andrew.social.login.vkontakte.initializer

import android.app.Application
import com.andrew.social.login.core.initializer.Initializer
import com.vk.sdk.VKSdk

/**
 * Created by Andrew on 01.07.2018
 */

class VkontakteInitializer : Initializer {

    override fun init(app: Application) {
        VKSdk.initialize(app)
    }
}