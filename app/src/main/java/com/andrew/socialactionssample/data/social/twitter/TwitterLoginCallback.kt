package com.andrew.socialactionssample.data.social.twitter

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

/**
 * Created by Andrew on 17.06.2018.
 */

class TwitterLoginCallback : Callback<TwitterSession>() {

    private val subject: AsyncSubject<Result<TwitterSession>> = AsyncSubject.create()

    override fun success(result: Result<TwitterSession>?) {
        result?.let {
            subject.onNext(it)
            subject.onComplete()
        }
    }

    override fun failure(exception: TwitterException?) {
        exception?.let { subject.onError(it) }
    }

    fun observe(): Single<Result<TwitterSession>> =
            subject.firstOrError()
                    .subscribeOn(Schedulers.io())
}