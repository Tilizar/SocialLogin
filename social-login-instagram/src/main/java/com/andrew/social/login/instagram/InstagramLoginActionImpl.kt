package com.andrew.social.login.instagram

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.webkit.CookieManager
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.instagram.misc.*
import com.andrew.social.login.instagram.view.InstagramLoginActivity

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramLoginActionImpl(activity: AppCompatActivity,
                               private var clientId: String,
                               private var redirectUrl: String,
                               private var scope: String = "") : SocialLoginAction(activity) {

    companion object {
        private const val INSTAGRAM_REQUEST_CODE = 12321
    }

    override fun login() {
        var authUrl = "$AUTH_URL$CLIENT_ID_DEF$clientId$REDIRECT_URI_DEF$redirectUrl$RESPONSE_TYPE_DEF"

        if (!TextUtils.isEmpty(scope)) {
            authUrl += "$SCOPE_TYPE_DEF$scope"
        }

        val intent = Intent(activity, InstagramLoginActivity::class.java)

        val bundle = Bundle()
        bundle.putString(InstagramLoginActivity.BUNDLE_AUTH_URL, authUrl)
        bundle.putString(InstagramLoginActivity.BUNDLE_REDIRECT_URL, redirectUrl)
        intent.putExtras(bundle)
        activity.startActivityForResult(intent, INSTAGRAM_REQUEST_CODE)
    }

    override fun logout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null)
        } else {
            CookieManager.getInstance().removeAllCookie()
        }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != INSTAGRAM_REQUEST_CODE) return
        if (resultCode == Activity.RESULT_OK) {
            val response = intent?.extras?.getString(InstagramLoginActivity.BUNDLE_TOKEN)
            response?.let { callback?.onSuccess(SocialType.INSTAGRAM, it) }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            val response = intent?.extras?.getSerializable(InstagramLoginActivity.BUNDLE_EXCEPTION)
            response?.let { callback?.onError(it as Exception) }
        }
    }

    override fun cancelRequest() {

    }
}