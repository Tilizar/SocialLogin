package com.andrew.social.login.yahoo.explicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 16.06.2018.
 */

class YahooLoginActionImpl(
    activity: Activity,
    clientId: String,
    redirectUrl: String,
    scope: String = ""
) : BaseWebSocialLoginAction(activity) {

    override val socialType: SocialType = SocialType.YAHOO

    override var url = "https://api.login.yahoo.com/oauth2/request_auth?" +
        "response_type=code" +
        "&client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""
}