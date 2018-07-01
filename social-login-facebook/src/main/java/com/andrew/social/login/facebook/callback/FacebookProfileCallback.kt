package com.andrew.social.login.facebook.callback

import com.andrew.social.login.facebook.exception.FacebookError
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.Profile
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookProfileCallback : GraphRequest.GraphJSONObjectCallback {

    private val subject: AsyncSubject<String> = AsyncSubject.create()

    override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
        if (`object` != null) {
            subject.onNext(internalGetName(`object`))
            subject.onComplete()
        } else {
            subject.onError(FacebookError())
        }
    }

    fun observe(): Single<String> {
        if (Profile.getCurrentProfile()?.name != null) {
            return Single.just(Profile.getCurrentProfile().name)
        }
        return Completable.fromAction { GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this).executeAsync() }
                .subscribeOn(Schedulers.newThread())
                .andThen(subject.firstOrError())
    }

    private fun internalGetName(jsonObject: JSONObject): String {
        val builder = StringBuilder()
        try {
            builder.append(jsonObject.getString("name"))
            return builder.toString()
        } catch (e: JSONException) {
            subject.onError(e)
        }

        return ""
    }
}