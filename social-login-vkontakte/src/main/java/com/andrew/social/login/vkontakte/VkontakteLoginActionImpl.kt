package com.andrew.social.login.vkontakte

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.vkontakte.callback.VkontakteRequestCallback
import com.andrew.social.login.vkontakte.callback.VkontakteTokenCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.VKServiceActivity
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiUserFull
import com.vk.sdk.api.model.VKList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Andrew on 16.06.2018.
 */

class VkontakteLoginActionImpl(activity: AppCompatActivity) : SocialLoginAction(activity) {

    private var loginDisposable: Disposable? = null
    private var logoutDisposable: Disposable? = null

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != VKServiceActivity.VKServiceType.Authorization.outerCode) return
        val tokenCallback = VkontakteTokenCallback()
        VKSdk.onActivityResult(requestCode, resultCode, intent, tokenCallback)
        var token = ""
        loginDisposable = tokenCallback.observe()
                .doOnSuccess { token = it.accessToken }
                .flatMap { getCurrentUser(it.userId) }
                .map { parseVkUserName(it.parsedModel) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback?.onSuccess(SocialType.VKONTAKTE, token, it) }, { callback?.onError(it) })
    }

    override fun cancelRequest() {
        loginDisposable = null
        logoutDisposable = null
    }

    override fun login() {
        VKSdk.login(activity)
    }

    override fun logout() {
        VKSdk.logout()
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
        return VkontakteRequestCallback(request)
                .observe()
    }
}