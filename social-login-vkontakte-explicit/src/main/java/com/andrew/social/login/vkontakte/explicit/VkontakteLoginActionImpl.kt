package com.andrew.social.login.vkontakte.explicit

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.view.WebViewLoginActivity

/**
 * Created by Andrew on 16.06.2018.
 */

class VkontakteLoginActionImpl(activity: AppCompatActivity,
                               clientId: String,
                               redirectUrl: String,
                               scope: String = "") : SocialLoginAction(activity) {

    companion object {
        private const val VKONTAKTE_REQUEST_CODE = 10000
    }

    private var url = "https://oauth.vk.com/authorize?" +
            "response_type=code" +
            "&client_id=$clientId" +
            "&redirect_uri=$redirectUrl"

    init {
        if (!TextUtils.isEmpty(scope)) {
            url += "&scope=$scope"
        }
    }

    override fun login() {
        WebViewLoginActivity.openLoginActivity(activity, VKONTAKTE_REQUEST_CODE, SocialType.VKONTAKTE, url)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != VKONTAKTE_REQUEST_CODE) return

        if (resultCode == Activity.RESULT_OK) {
            val response = intent?.extras?.getString(WebViewLoginActivity.BUNDLE_CODE)
            response?.let { callback?.onSuccess(SocialType.VKONTAKTE, ResponseType.SERVER_CODE, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val response = intent?.extras?.getSerializable(WebViewLoginActivity.BUNDLE_EXCEPTION)
            response?.let { callback?.onError(it as Exception) }
        }
    }
}