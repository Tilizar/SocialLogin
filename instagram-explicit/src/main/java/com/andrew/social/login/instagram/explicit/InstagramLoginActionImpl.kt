package com.andrew.social.login.instagram.explicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramLoginActionImpl(
    activity: Activity,
    clientId: String,
    redirectUrl: String,
    scope: String = ""
) : BaseWebSocialLoginAction(activity) {

    override val socialType: SocialType = SocialType.INSTAGRAM

    override var url = "https://instagram.com/oauth/authorize/" +
        "?client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        "&response_type=code" +
        if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""
}