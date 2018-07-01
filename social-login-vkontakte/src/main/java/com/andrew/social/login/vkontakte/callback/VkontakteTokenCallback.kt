package com.andrew.social.login.vkontakte.callback

import com.andrew.social.login.vkontakte.exception.VkontakteException
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.api.VKError
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

/**
 * Created by Andrew on 17.06.2018.
 */

class VkontakteTokenCallback : VKCallback<VKAccessToken> {

    private var subject: AsyncSubject<VKAccessToken> = AsyncSubject.create()

    override fun onResult(res: VKAccessToken?) {
        res?.let {
            subject.onNext(it)
            subject.onComplete()
        }
    }

    override fun onError(error: VKError?) {
        subject.onError(VkontakteException(error?.errorMessage))
    }

    fun observe(): Single<VKAccessToken> =
            subject.firstOrError()
                    .subscribeOn(Schedulers.io())
}