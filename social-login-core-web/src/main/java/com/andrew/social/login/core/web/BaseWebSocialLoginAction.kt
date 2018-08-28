package com.andrew.social.login.core.web

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_CODE


abstract class BaseWebSocialLoginAction(activity: Activity,
                                        private val socialType: SocialType,
                                        private val socialRequestCode: Int,
                                        private val responseType: ResponseType = ResponseType.SERVER_CODE) : SocialLoginAction(activity) {

    abstract val url: String

    override fun login() {
        WebActivityStarter.openLoginActivity(activity, socialRequestCode, url, socialType = socialType, responseType = responseType)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != socialRequestCode) return

        if (resultCode == Activity.RESULT_OK) {
            intent?.extras?.getString(BUNDLE_CODE)?.let { callback?.onSuccess(socialType, responseType, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            callback?.onError(SocialLoginException(socialType))
        }
    }
}