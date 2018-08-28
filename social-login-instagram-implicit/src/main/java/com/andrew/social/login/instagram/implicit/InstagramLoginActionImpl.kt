package com.andrew.social.login.instagram.implicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction
import com.andrew.social.login.core.web.UrlHandler
import com.andrew.social.login.core.web.WebActivityStarter
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_REDIRECT_URL

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramLoginActionImpl(activity: Activity,
                               clientId: String,
                               private val redirectUrl: String,
                               scope: String = "") : BaseWebSocialLoginAction(activity, SocialType.INSTAGRAM, INSTAGRAM_REQUEST_CODE, ResponseType.TOKEN) {

    init {
        UrlHandler.putHandler(SocialType.INSTAGRAM, ResponseType.TOKEN) { url, webActivity ->
            val redirectUrl = webActivity.intent.extras?.getString(BUNDLE_REDIRECT_URL)
            if (url == null || redirectUrl == null) return@putHandler false
            return@putHandler if (url.startsWith(redirectUrl)) {
                if (url.contains(RESPONSE_TOKEN)) {
                    val tokenArray = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (tokenArray.size > 1) {
                        webActivity.finishWithSuccess(tokenArray[1])
                    } else {
                        webActivity.finishWithError()
                    }
                } else if (url.contains(RESPONSE_ERROR)) {
                    webActivity.finishWithError()
                }
                true
            } else false
        }
    }

    companion object {
        private const val INSTAGRAM_REQUEST_CODE = 10001

        const val RESPONSE_TOKEN = "access_token"
        const val RESPONSE_ERROR = "error"
    }

    override var url = "https://instagram.com/oauth/authorize/" +
            "?client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&response_type=token" +
            if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""

    override fun login() {
        WebActivityStarter.openLoginActivity(activity, INSTAGRAM_REQUEST_CODE, url, redirectUrl, SocialType.INSTAGRAM, ResponseType.TOKEN)
    }
}