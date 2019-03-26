package com.andrew.social.login.amazon.explicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 24.06.2018
 */

class AmazonLoginActionImpl(
    activity: Activity,
    clientId: String,
    redirectUrl: String,
    scope: String = "profile"
) : BaseWebSocialLoginAction(activity) {

    override val socialType: SocialType = SocialType.AMAZON

    override var url = "https://www.amazon.com/ap/oa/" +
        "?client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        "&response_type=code" +
        if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""
}