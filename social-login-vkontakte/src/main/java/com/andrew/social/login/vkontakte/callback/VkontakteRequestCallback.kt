package com.andrew.social.login.vkontakte.callback

import com.andrew.social.login.vkontakte.exception.VkontakteException
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

/**
 * Created by Andrew on 17.06.2018.
 */

class VkontakteRequestCallback(request: VKRequest) : VKRequest.VKRequestListener() {

    private var subject: AsyncSubject<VKResponse> = AsyncSubject.create()

    init {
        request.executeWithListener(this)
    }

    override fun onComplete(response: VKResponse?) {
        response?.let {
            subject.onNext(it)
            subject.onComplete()
        }
    }

    override fun onError(error: VKError?) {
        subject.onError(VkontakteException(error?.errorMessage))
    }

    fun observe(): Single<VKResponse> =
            subject.firstOrError()
                    .subscribeOn(Schedulers.io())
}