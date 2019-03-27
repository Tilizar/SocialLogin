package com.andrew.social.login.microsoft.explicit

import android.app.Activity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 16.06.2018.
 */

class MicrosoftLoginActionImpl(
    activity: Activity,
    clientId: String,
    redirectUrl: String,
    scope: String = "openid"
) : BaseWebSocialLoginAction(activity) {

    override val socialType: SocialType = SocialType.MICROSOFT

    override var url = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?" +
        "response_type=code" +
        "&client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        "&scope=$scope"
}