package com.andrew.socia.login.facebook.implicit.callback

import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.exception.SocialLoginException
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

    }

    override fun onError(error: FacebookException?) {
        subject.onError(SocialLoginException(SocialType.FACEBOOK))
    }

    fun recreate() {
        subject = AsyncSubject.create()
    }

    fun observe(): Single<String> = subject.firstOrError()
            .subscribeOn(Schedulers.newThread())
}