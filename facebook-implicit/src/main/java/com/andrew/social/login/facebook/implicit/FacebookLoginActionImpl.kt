package com.andrew.social.login.facebook.implicit

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginActionImpl(
    activity: FragmentActivity,
    private val readPermissions: List<String> = emptyList()
) : SocialLoginAction(activity), LifecycleObserver {

    private val callbackManager by lazy { CallbackManager.Factory.create() }

    private val facebookCallback by lazy {
        object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.accessToken?.token?.let {
                    callback?.onSuccess(SocialType.FACEBOOK, ResponseType.TOKEN, it)
                }
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                callback?.onError(SocialLoginException(SocialType.FACEBOOK))
            }
        }
    }

    init {
        activity.lifecycle.addObserver(this)
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)
    }

    override fun login() {
        LoginManager.getInstance().logInWithReadPermissions(activity, readPermissions)
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, intent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }
}