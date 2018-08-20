package com.andrew.social.login.facebook.explicit

import android.app.Activity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.BaseWebSocialLoginAction

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookLoginActionImpl(activity: Activity,
                              clientId: String,
                              redirectUrl: String) : BaseWebSocialLoginAction(activity, SocialType.FACEBOOK, FACEBOOK_REQUEST_CODE) {

    companion object {
        private const val FACEBOOK_REQUEST_CODE = 10002
    }

    override val url = "https://www.facebook.com/v3.0/dialog/oauth?" +
            "client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&state={state-param}"
}