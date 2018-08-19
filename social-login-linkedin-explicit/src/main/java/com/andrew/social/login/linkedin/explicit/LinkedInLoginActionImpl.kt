package com.andrew.social.login.linkedin.explicit

import android.app.Activity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.BaseWebSocialLoginAction

/**
 * Created by Andrew on 15.07.2018
 */
class LinkedInLoginActionImpl(activity: Activity,
                              clientId: String,
                              redirectUrl: String) : BaseWebSocialLoginAction(activity, SocialType.GITHUB, LINKED_IN_REQUEST_CODE) {

    companion object {
        private const val LINKED_IN_REQUEST_CODE = 10004
    }

    override val url = "https://www.linkedin.com/oauth/v2/authorization" +
            "?response_type=code" +
            "&client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            "&state=randomstridkwhyIneedit"
}