package com.andrew.social.login.core.web

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType

/**
 * Created by Andrey Matyushin on 28.08.2018
 */

object WebActivityStarter {

    const val BUNDLE_URL = "BUNDLE_URL"
    const val BUNDLE_REDIRECT_URL = "BUNDLE_REDIRECT_URL"
    const val BUNDLE_SOCIAL_TYPE = "BUNDLE_SOCIAL_TYPE"
    const val BUNDLE_RESPONSE_TYPE = "BUNDLE_RESPONSE_TYPE"

    const val BUNDLE_CODE = "BUNDLE_CODE"

    private var loginActivity: Class<*>? = null

    fun <T : BaseWebViewLoginActivity> setWebLoginActivity(clazz: Class<T>) {
        loginActivity = clazz
    }

    fun openLoginActivity(activity: Activity,
                          requestCode: Int,
                          url: String,
                          redirectUrl: String = "",
                          socialType: SocialType,
                          responseType: ResponseType) {

        val clazz = loginActivity?.let {
            it
        } ?: WebViewLoginActivity::class.java

        val intent = Intent(activity, clazz).apply {
            putExtra(BUNDLE_URL, url)
            if (!redirectUrl.isEmpty()) {
                putExtra(BUNDLE_REDIRECT_URL, redirectUrl)
            }
            putExtra(BUNDLE_SOCIAL_TYPE, socialType)
            putExtra(BUNDLE_RESPONSE_TYPE, responseType)
        }

        intent.resolveActivity(activity.packageManager)

        activity.startActivityForResult(intent, requestCode)
    }
}