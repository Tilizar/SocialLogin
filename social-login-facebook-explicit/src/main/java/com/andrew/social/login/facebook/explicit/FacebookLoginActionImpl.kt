package com.andrew.social.login.facebook.explicit

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.view.WebViewLoginActivity

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginActionImpl(activity: AppCompatActivity,
                              clientId: String,
                              redirectUrl: String) : SocialLoginAction(activity) {

    companion object {
        private const val FACEBOOK_REQUEST_CODE = 10002
    }

    private val url = "https://www.facebook.com/v3.0/dialog/oauth?" +
            "client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&state={state-param}"

    override fun login() {
        WebViewLoginActivity.openLoginActivity(activity, FACEBOOK_REQUEST_CODE, SocialType.FACEBOOK, url)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != FACEBOOK_REQUEST_CODE) return

        if (resultCode == Activity.RESULT_OK) {
            val response = intent?.extras?.getString(WebViewLoginActivity.BUNDLE_CODE)
            response?.let { callback?.onSuccess(SocialType.FACEBOOK, ResponseType.SERVER_CODE, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val response = intent?.extras?.getSerializable(WebViewLoginActivity.BUNDLE_EXCEPTION)
            response?.let { callback?.onError(it as Exception) }
        }
    }
}