package com.andrew.socialactionssample.data.social.vk.callback

import com.andrew.socialactionssample.data.social.vk.exception.VkException
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

/**
 * Created by Andrew on 17.06.2018.
 */

class VkRxLoginStateCallback : VKCallback<VKSdk.LoginState> {

    private var subject: AsyncSubject<VKSdk.LoginState> = AsyncSubject.create()

    override fun onResult(res: VKSdk.LoginState?) {
        res?.let {
            subject.onNext(it)
            subject.onComplete()
        }
    }

    override fun onError(error: VKError?) {
        subject.onError(VkException(error?.errorMessage))
    }

    fun observeVkLogoutState() : Single<VKSdk.LoginState> =
            subject.filter { it == VKSdk.LoginState.LoggedOut }
            .firstOrError()
            .subscribeOn(Schedulers.io())
}