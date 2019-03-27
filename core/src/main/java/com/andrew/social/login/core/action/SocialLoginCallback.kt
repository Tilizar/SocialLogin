package com.andrew.social.login.core.action

import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType

/**
 * Created by Andrew on 16.08.2018
 */

interface SocialLoginCallback {

    fun onSuccess(socialType: SocialType, responseType: ResponseType, response: String)

    fun onError(error: Throwable)
}