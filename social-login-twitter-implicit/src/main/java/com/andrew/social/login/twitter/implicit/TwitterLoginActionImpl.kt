package com.andrew.social.login.twitter.implicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Andrew on 17.06.2018.
 */

class TwitterLoginActionImpl(activity: Activity) : SocialLoginAction(activity) {

    private val authClient: TwitterAuthClient = TwitterAuthClient()
    private var loginCallback: TwitterLoginCallback? = null

    private var disposable: Disposable? = null

    override fun login() {
        loginCallback = TwitterLoginCallback()
        authClient.authorize(activity, loginCallback)
    }

    override fun logout() {
        TwitterCore.getInstance().sessionManager.clearActiveSession()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) return
        authClient.onActivityResult(requestCode, resultCode, intent)
        loginCallback?.let { callback ->
            disposable = callback.observe()
                    .map { it.data.authToken.token }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ this.callback?.onSuccess(SocialType.TWITTER, ResponseType.TOKEN, it) },
                            { this.callback?.onError(it) })
        }
    }

    override fun cancelRequest() {
        disposable = null
    }
}