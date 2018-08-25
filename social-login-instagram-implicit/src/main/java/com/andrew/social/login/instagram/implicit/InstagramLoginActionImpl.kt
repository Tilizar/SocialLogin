package com.andrew.social.login.instagram.implicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.BaseWebSocialLoginAction

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramLoginActionImpl(activity: Activity,
                               clientId: String,
                               private val redirectUrl: String,
                               scope: String = "") : BaseWebSocialLoginAction(activity, SocialType.INSTAGRAM, INSTAGRAM_REQUEST_CODE, ResponseType.TOKEN) {

    companion object {
        private const val INSTAGRAM_REQUEST_CODE = 10001
    }

    override var url = "https://instagram.com/oauth/authorize/" +
            "?client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&response_type=token" +
            if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""

    override fun login() {
        InstagramLoginActivity.openInstagramLoginActivity(activity, INSTAGRAM_REQUEST_CODE, url, redirectUrl)
    }
}