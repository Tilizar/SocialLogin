package com.andrew.social.login.vkontakte.implicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.vk.sdk.VKSdk
import com.vk.sdk.VKServiceActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Andrew on 16.06.2018.
 */

class VkontakteLoginActionImpl(activity: Activity) : SocialLoginAction(activity) {

    private var loginDisposable: Disposable? = null

    override fun login() {
        VKSdk.login(activity)
    }

    override fun logout() {
        VKSdk.logout()
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != VKServiceActivity.VKServiceType.Authorization.outerCode) return
        val tokenCallback = VkontakteTokenCallback()
        VKSdk.onActivityResult(requestCode, resultCode, intent, tokenCallback)
        loginDisposable = tokenCallback.observe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback?.onSuccess(SocialType.VKONTAKTE, ResponseType.TOKEN, it.accessToken) },
                        { callback?.onError(it) })
    }

    override fun cancelRequest() {
        loginDisposable = null
    }
}