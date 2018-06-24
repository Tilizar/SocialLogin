package com.andrew.socialactionssample.data.social.facebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.data.social.SocialType
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.facebook.callback.FacebookLoginCallback
import com.andrew.socialactionssample.data.social.facebook.callback.FacebookProfileCallback
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginActionImpl(activity: AppCompatActivity) : SocialLoginAction(activity) {

    companion object {
        internal const val profilePermission = "public_profile"
    }

    private val callbackManager = CallbackManager.Factory.create()
    private val loginCallback = FacebookLoginCallback()

    private var disposable: Disposable? = null

    init {
        LoginManager.getInstance().registerCallback(callbackManager, loginCallback)
    }

    override fun login() {
        LoginManager.getInstance().logInWithReadPermissions(activity, arrayListOf(profilePermission))
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        loginCallback.recreate()
        val profileCallback = FacebookProfileCallback()
        if (!callbackManager.onActivityResult(requestCode, resultCode, intent)) return
        disposable = Single.zip(loginCallback.observe(), profileCallback.observe(),
                BiFunction { token: String, name: String -> Pair(token, name) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback?.onSuccess(SocialType.FACEBOOK, it.first, it.second) },
                        { callback?.onError(it) })
    }

    override fun cancelRequest() {
        LoginManager.getInstance().unregisterCallback(callbackManager)
        disposable = null
    }
}