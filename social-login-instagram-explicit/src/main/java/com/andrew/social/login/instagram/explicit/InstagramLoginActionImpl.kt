package com.andrew.social.login.instagram.explicit

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.view.WebViewLoginActivity

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramLoginActionImpl(activity: AppCompatActivity,
                               clientId: String,
                               redirectUrl: String,
                               scope: String = "") : SocialLoginAction(activity) {

    companion object {
        private const val INSTAGRAM_REQUEST_CODE = 10001
    }

    private var url = "https://instagram.com/oauth/authorize/" +
            "?client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&response_type=code"

    init {
        if (!TextUtils.isEmpty(scope)) {
            url += "&scope=$scope"
        }
    }

    override fun login() {
        WebViewLoginActivity.openLoginActivity(activity, INSTAGRAM_REQUEST_CODE, SocialType.INSTAGRAM, url)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != INSTAGRAM_REQUEST_CODE) return
        if (resultCode == Activity.RESULT_OK) {
            val response = intent?.extras?.getString(WebViewLoginActivity.BUNDLE_CODE)
            response?.let { callback?.onSuccess(SocialType.INSTAGRAM, ResponseType.SERVER_CODE, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val response = intent?.extras?.getSerializable(WebViewLoginActivity.BUNDLE_EXCEPTION)
            response?.let { callback?.onError(it as Exception) }
        }
    }
}