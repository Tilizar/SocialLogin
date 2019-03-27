package com.andrew.social.login.vk.explicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 16.06.2018.
 */

class VkLoginActionImpl(
    activity: Activity,
    clientId: String,
    redirectUrl: String,
    scope: String = ""
) : BaseWebSocialLoginAction(activity) {

    override val socialType: SocialType = SocialType.VK

    override var url = "https://oauth.vk.com/authorize?" +
        "response_type=code" +
        "&client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""
}