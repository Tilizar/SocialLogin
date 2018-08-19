package com.andrew.social.login.core.action

import android.content.Intent
import android.os.Build
import android.webkit.CookieManager
import com.andrew.social.login.core.SocialType

/**
 * Created by Andrew on 16.06.2018.
 */

class SocialLoginManager(private val socialLoginActions: Map<SocialType, SocialLoginAction>) {

    fun observeLoginCallback(callbackSocial: SocialLoginCallback) {
        socialLoginActions.forEach { it.value.callback = callbackSocial }
    }

    fun disposeLoginCallback() {
        socialLoginActions.forEach {
            it.value.cancelRequest()
            it.value.callback = null
        }
    }

    fun login(socialType: SocialType) {
        socialLoginActions[socialType]?.login()
    }

    fun logout() {
        socialLoginActions.forEach { it.value.logout() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null)
        } else {
            CookieManager.getInstance().removeAllCookie()
        }
    }

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        socialLoginActions.forEach { it.value.handleResult(requestCode, resultCode, data) }
    }
}