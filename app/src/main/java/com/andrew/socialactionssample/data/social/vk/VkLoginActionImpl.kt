package com.andrew.socialactionssample.data.social.vk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.qualifier.SocialType
import com.andrew.socialactionssample.data.social.vk.callback.VkRxLoginStateCallback
import com.andrew.socialactionssample.data.social.vk.callback.VkRxRequestCallback
import com.andrew.socialactionssample.data.social.vk.callback.VkRxTokenCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiUserFull
import com.vk.sdk.api.model.VKList
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Andrew on 16.06.2018.
 */

class VkLoginActionImpl(activity: AppCompatActivity) : SocialLoginAction(activity) {

    private var loginDisposable: Disposable? = null
    private var logoutDisposable: Disposable? = null

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val tokenCallback = VkRxTokenCallback()
        VKSdk.onActivityResult(requestCode, resultCode, intent, tokenCallback)
        var token = ""
        loginDisposable = tokenCallback.observeVkToken()
                .doOnSuccess { token = it.accessToken }
                .flatMap { getCurrentUser(it.userId) }
                .map { parseVkUserName(it.parsedModel) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback?.onSuccessLogin(SocialType.VK, token, it) }, { callback?.onError(it) })
    }

    override fun cancelRequest() {
        loginDisposable = null
        logoutDisposable = null
    }

    override fun login() {
        VKSdk.login(activity)
    }

    override fun logout() {
        val loginStateCallback = VkRxLoginStateCallback()
        logoutDisposable = Completable.fromAction { VKSdk.logout() }
                .doOnComplete { VKSdk.wakeUpSession(activity, loginStateCallback) }
                .andThen(loginStateCallback.observeVkLogoutState())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback?.onSuccessLogout(SocialType.VK) }, { callback?.onError(it) })
    }

    private fun parseVkUserName(modelToParse: Any): String {
        if (modelToParse is VKList<*>
                && !modelToParse.isEmpty()
                && modelToParse[0] is VKApiUserFull) {
            return modelToParse[0].toString()
        }
        return ""
    }

    private fun getCurrentUser(userId: String) : Single<VKResponse> {
        val request = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, userId))
        return VkRxRequestCallback(request)
                .observeVkRequest()
    }

    private enum class Type {
        LOGIN, LOGOUT
    }
}