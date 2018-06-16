package com.andrew.socialactionssample.data.social.vk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.qualifier.SocialType
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

/**
 * Created by Andrew on 16.06.2018.
 */

class VkLoginActionImpl (activity: AppCompatActivity)
    : SocialLoginAction(activity), VKCallback<VKAccessToken> {

    override fun onResult(res: VKAccessToken?) {
        res?.accessToken?.let { callback?.onSuccess(SocialType.VK, it) }
    }

    override fun onError(error: VKError?) {
        if (error?.errorCode != VKError.VK_CANCELED) {
            callback?.onError(Throwable(error?.errorMessage))
        }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        VKSdk.onActivityResult(requestCode, resultCode, intent, this)
    }

    override fun cancelRequest() {

    }

    override fun login() {
        VKSdk.login(activity)
    }

    override fun logout() {
        VKSdk.logout()
    }
}