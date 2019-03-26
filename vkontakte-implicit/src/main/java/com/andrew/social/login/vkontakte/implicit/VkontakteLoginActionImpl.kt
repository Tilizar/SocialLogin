package com.andrew.social.login.vkontakte.implicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

/**
 * Created by Andrew on 16.06.2018.
 */

class VkontakteLoginActionImpl(activity: Activity) : SocialLoginAction(activity) {

    private val vkCallback by lazy {
        object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                callback?.onSuccess(SocialType.VKONTAKTE, ResponseType.TOKEN, token.accessToken)
            }

            override fun onLoginFailed(errorCode: Int) {
                callback?.onError(SocialLoginException(SocialType.VKONTAKTE))
            }
        }
    }

    override fun login() {
        VK.login(activity)
    }

    override fun logout() {
        VK.logout()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        VK.onActivityResult(requestCode, resultCode, intent, vkCallback)
    }
}