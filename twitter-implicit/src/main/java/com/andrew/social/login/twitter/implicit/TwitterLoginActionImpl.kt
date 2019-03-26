package com.andrew.social.login.twitter.implicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient

/**
 * Created by Andrew on 17.06.2018.
 */

class TwitterLoginActionImpl(activity: Activity) : SocialLoginAction(activity) {

    private val authClient: TwitterAuthClient by lazy { TwitterAuthClient() }

    private val twitterCallback: Callback<TwitterSession> by lazy {
        object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                result?.data?.authToken?.token?.let {
                    callback?.onSuccess(SocialType.TWITTER, ResponseType.TOKEN, it)
                }
            }

            override fun failure(exception: TwitterException?) {
                callback?.onError(SocialLoginException(SocialType.TWITTER))
            }
        }
    }

    override fun login() {
        authClient.authorize(activity, twitterCallback)
    }

    override fun logout() {
        TwitterCore.getInstance().sessionManager.clearActiveSession()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) return
        authClient.onActivityResult(requestCode, resultCode, intent)
    }
}