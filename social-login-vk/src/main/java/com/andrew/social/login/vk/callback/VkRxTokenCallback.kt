package com.andrew.social.login.vk.callback

import com.andrew.social.login.vk.exception.VkException
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.api.VKError
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

/**
 * Created by Andrew on 17.06.2018.
 */

class VkRxTokenCallback : VKCallback<VKAccessToken> {

    private var subject: AsyncSubject<VKAccessToken> = AsyncSubject.create()

    override fun onResult(res: VKAccessToken?) {
        res?.let {
            subject.onNext(it)
            subject.onComplete()
        }
    }

    override fun onError(error: VKError?) {
        subject.onError(VkException(error?.errorMessage))
    }

    fun observe(): Single<VKAccessToken> =
            subject.firstOrError()
                    .subscribeOn(Schedulers.io())
}