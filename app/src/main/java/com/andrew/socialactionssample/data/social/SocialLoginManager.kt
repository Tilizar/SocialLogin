package com.andrew.socialactionssample.data.social

import android.content.Intent
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.qualifier.SocialType

/**
 * Created by Andrew on 16.06.2018.
 */

class SocialLoginManager (private val socialLoginActions: Map<SocialType, SocialLoginAction>) {

    interface LoginCallback {
        fun onSuccess(socialType: SocialType, token: String)
        fun onError(error: Throwable)
    }

    fun observeLoginCallback(callback: LoginCallback) {
        socialLoginActions.forEach { it.value.callback = callback }
    }

    fun disposeLoginCallback() {
        socialLoginActions.forEach { it.value.callback = null }
    }

    fun login(socialType: SocialType) {
        socialLoginActions[socialType]?.login()
    }

    fun logout() {
        socialLoginActions.forEach { it.value.logout() }
    }

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        socialLoginActions.forEach { it.value.handleResult(requestCode, resultCode, data) }
    }

    fun stop() {
        socialLoginActions.forEach { it.value.cancelRequest() }
    }
}