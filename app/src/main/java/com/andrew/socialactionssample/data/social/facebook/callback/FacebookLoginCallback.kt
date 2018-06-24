package com.andrew.socialactionssample.data.social.facebook.callback

import com.andrew.socialactionssample.data.social.facebook.exception.FacebookCancelError
import com.andrew.socialactionssample.data.social.facebook.exception.FacebookError
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginCallback : FacebookCallback<LoginResult> {

    private var subject: AsyncSubject<String> = AsyncSubject.create()

    override fun onSuccess(result: LoginResult?) {
        result?.let {
            subject.onNext(it.accessToken.token)
            subject.onComplete()
        }
    }

    override fun onCancel() {
        subject.onError(FacebookCancelError())
    }

    override fun onError(error: FacebookException?) {
        if (error != null) subject.onError(FacebookError(error.message)) else subject.onError(FacebookError())
    }

    fun recreate() {
        subject = AsyncSubject.create()
    }

    fun observe(): Single<String> = subject.firstOrError()
            .subscribeOn(Schedulers.newThread())
}