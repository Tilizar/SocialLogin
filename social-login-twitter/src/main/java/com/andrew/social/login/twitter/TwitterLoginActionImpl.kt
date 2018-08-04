package com.andrew.social.login.twitter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
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

class TwitterLoginActionImpl(activity: AppCompatActivity) : SocialLoginAction(activity) {

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
        loginCallback?.let {
            disposable = it.observe()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ callback?.onSuccess(SocialType.TWITTER, it.data.authToken.token) },
                            { callback?.onError(it) })
        }
    }

    override fun cancelRequest() {
        disposable = null
    }
}