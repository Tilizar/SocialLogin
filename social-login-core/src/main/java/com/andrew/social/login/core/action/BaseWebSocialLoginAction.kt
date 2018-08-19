package com.andrew.social.login.core.action

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.exception.SocialLoginException
import com.andrew.social.login.core.view.WebViewLoginActivity


abstract class BaseWebSocialLoginAction(activity: Activity,
                                        private val socialType: SocialType,
                                        private val socialRequestCode: Int,
                                        private val responseType: ResponseType = ResponseType.SERVER_CODE) : SocialLoginAction(activity) {

    abstract val url: String

    override fun login() {
        WebViewLoginActivity.openLoginActivity(activity, socialRequestCode, url)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != socialRequestCode) return

        if (resultCode == Activity.RESULT_OK) {
            intent?.extras?.getString(WebViewLoginActivity.BUNDLE_CODE)?.let { callback?.onSuccess(socialType, responseType, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            callback?.onError(SocialLoginException(socialType))
        }
    }
}