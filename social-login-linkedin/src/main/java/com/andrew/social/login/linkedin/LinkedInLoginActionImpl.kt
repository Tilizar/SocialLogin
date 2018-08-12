package com.andrew.social.login.linkedin

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.webkit.CookieManager
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.view.WebViewLoginActivity

/**
 * Created by Andrew on 15.07.2018
 */
class LinkedInLoginActionImpl(activity: AppCompatActivity,
                              clientId: String,
                              redirectUrl: String) : SocialLoginAction(activity) {

    companion object {
        const val LINKED_IN_REQUEST_CODE = 10004
    }

    private val url = "https://www.linkedin.com/oauth/v2/authorization" +
            "?response_type=code" +
            "&client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&state=randomstridkwhyIneedit"

    override fun login() {
        WebViewLoginActivity.openLoginActivity(activity, LINKED_IN_REQUEST_CODE, SocialType.LINKED_IN, url)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != LINKED_IN_REQUEST_CODE) return
        if (resultCode == Activity.RESULT_OK) {
            val response = intent?.extras?.getString(WebViewLoginActivity.BUNDLE_CODE)
            response?.let { callback?.onSuccess(SocialType.LINKED_IN, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val response = intent?.extras?.getSerializable(WebViewLoginActivity.BUNDLE_EXCEPTION)
            response?.let { callback?.onError(it as Exception) }
        }
    }
}