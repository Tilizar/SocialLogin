package com.andrew.social.login.facebook.explicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.randomString
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginActionImpl(
    activity: Activity,
    clientId: String,
    redirectUrl: String,
    scope: String = ""
) : BaseWebSocialLoginAction(activity) {

    override val socialType: SocialType = SocialType.FACEBOOK

    override val url = "https://www.facebook.com/v3.2/dialog/oauth?" +
        "client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        "&state=${randomString()}" +
        if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""
}