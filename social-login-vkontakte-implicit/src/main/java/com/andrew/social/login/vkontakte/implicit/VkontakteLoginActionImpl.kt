package com.andrew.social.login.vkontakte.implicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.VKServiceActivity
import com.vk.sdk.api.VKError

/**
 * Created by Andrew on 16.06.2018.
 */

class VkontakteLoginActionImpl(activity: Activity) : SocialLoginAction(activity) {

    override fun login() {
        VKSdk.login(activity)
    }

    override fun logout() {
        VKSdk.logout()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != VKServiceActivity.VKServiceType.Authorization.outerCode) return
        VKSdk.onActivityResult(requestCode, resultCode, intent, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken?) {
                res?.accessToken?.let {
                    callback?.onSuccess(SocialType.VKONTAKTE, ResponseType.TOKEN, it)
                }
            }

            override fun onError(error: VKError?) {
                callback?.onError(SocialLoginException(SocialType.VKONTAKTE))
            }
        })
    }
}