package com.andrew.social.login.facebook.implicit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.facebook.implicit.callback.FacebookLoginCallback
import com.andrew.social.login.core.ResponseType
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginActionImpl(activity: AppCompatActivity,
                              private val readPermissions: List<String> = emptyList()) : SocialLoginAction(activity) {

    private val callbackManager = CallbackManager.Factory.create()
    private val loginCallback = FacebookLoginCallback()

    private var disposable: Disposable? = null

    init {
        LoginManager.getInstance().registerCallback(callbackManager, loginCallback)
    }

    override fun login() {
        LoginManager.getInstance().logInWithReadPermissions(activity, readPermissions)
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        loginCallback.recreate()
        if (!callbackManager.onActivityResult(requestCode, resultCode, intent)) return
        disposable = loginCallback.observe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback?.onSuccess(SocialType.FACEBOOK, ResponseType.TOKEN, it) },
                        { callback?.onError(it) })
    }

    override fun cancelRequest() {
        LoginManager.getInstance().unregisterCallback(callbackManager)
        disposable = null
    }
}