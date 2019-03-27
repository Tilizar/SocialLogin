package com.andrew.social.login.amazon.implicit

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.amazon.identity.auth.device.AuthError
import com.amazon.identity.auth.device.api.Listener
import com.amazon.identity.auth.device.api.authorization.*
import com.amazon.identity.auth.device.api.workflow.RequestContext
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException

/**
 * Created by Andrew on 24.06.2018
 */

class AmazonLoginActionImpl(
    activity: FragmentActivity
) : SocialLoginAction(activity), LifecycleObserver {

    private val requestContext by lazy { RequestContext.create(activity as Context) }

    private val amazonCallback by lazy {
        object : AuthorizeListener() {
            override fun onSuccess(result: AuthorizeResult?) {
                result?.accessToken?.let {
                    callback?.onSuccess(SocialType.AMAZON, ResponseType.TOKEN, it)
                }
            }

            override fun onCancel(cancelation: AuthCancellation?) {

            }

            override fun onError(error: AuthError?) {
                callback?.onError(SocialLoginException(SocialType.AMAZON))
            }

        }
    }

    private val loginRequest by lazy {
        AuthorizeRequest.Builder(requestContext)
            .addScope(ProfileScope.profile())
            .build()
    }

    private val logoutListener by lazy {
        object : Listener<Void, AuthError> {
            override fun onSuccess(p0: Void?) {}
            override fun onError(p0: AuthError?) {}
        }
    }

    init {
        activity.lifecycle.addObserver(this)
        requestContext.registerListener(amazonCallback)
    }

    override fun login() {
        AuthorizationManager.authorize(loginRequest)
    }

    override fun logout() {
        AuthorizationManager.signOut(activity, logoutListener)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        requestContext.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        requestContext.unregisterListener(amazonCallback)
    }
}