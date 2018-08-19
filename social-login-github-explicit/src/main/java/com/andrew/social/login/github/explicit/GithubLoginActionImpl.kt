package com.andrew.social.login.github.explicit

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.view.WebViewLoginActivity

/**
 * Created by Andrew on 15.07.2018
 */
class GithubLoginActionImpl(activity: AppCompatActivity,
                            clientId: String,
                            redirectUrl: String,
                            scope: String = "") : SocialLoginAction(activity) {

    companion object {
        private const val GITHUB_REQUEST_CODE = 10005
    }

    private var url = "https://github.com/login/oauth/authorize" +
            "?client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&state=rndm_str_to_protect_ur_auth"

    init {
        if (!TextUtils.isEmpty(scope)) {
            url += "&scope=$scope"
        }
    }

    override fun login() {
        WebViewLoginActivity.openLoginActivity(activity, GITHUB_REQUEST_CODE, SocialType.GITHUB, url)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != GITHUB_REQUEST_CODE) return
        if (resultCode == Activity.RESULT_OK) {
            val response = intent?.extras?.getString(WebViewLoginActivity.BUNDLE_CODE)
            response?.let { callback?.onSuccess(SocialType.GITHUB, ResponseType.SERVER_CODE, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val response = intent?.extras?.getSerializable(WebViewLoginActivity.BUNDLE_EXCEPTION)
            response?.let { callback?.onError(it as Exception) }
        }
    }
}