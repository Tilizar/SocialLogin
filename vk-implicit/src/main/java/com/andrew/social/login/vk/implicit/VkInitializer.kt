package com.andrew.social.login.vk.implicit

import android.app.Application
import com.andrew.social.login.core.initializer.Initializer
import com.vk.api.sdk.VK

/**
 * Created by Andrew on 01.07.2018
 */

class VkInitializer : Initializer {

    override fun init(app: Application) {
        VK.initialize(app)
    }
}