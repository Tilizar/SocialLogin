package com.andrew.social.login.core.web

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_CODE


abstract class BaseWebSocialLoginAction(
    activity: Activity,
    private val responseType: ResponseType = ResponseType.SERVER_CODE
) : SocialLoginAction(activity) {

    abstract val socialType: SocialType

    val requestCode: Int by lazy { socialType.ordinal }

    abstract val url: String

    override fun login() {
        WebActivityStarter.openLoginActivity(activity, requestCode, url, socialType = socialType, responseType = responseType)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != this.requestCode) return
        if (resultCode == Activity.RESULT_OK) {
            intent?.extras?.getString(BUNDLE_CODE)?.let { callback?.onSuccess(socialType, responseType, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            callback?.onError(SocialLoginException(socialType))
        }
    }
}