package com.andrew.social.login.vkontakte.explicit

import android.app.Activity
import android.text.TextUtils
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.BaseWebSocialLoginAction

/**
 * Created by Andrew on 16.06.2018.
 */

class VkontakteLoginActionImpl(activity: Activity,
                               clientId: String,
                               redirectUrl: String,
                               scope: String = "") : BaseWebSocialLoginAction(activity, SocialType.VKONTAKTE, VKONTAKTE_REQUEST_CODE) {

    companion object {
        private const val VKONTAKTE_REQUEST_CODE = 10000
    }

    override var url = "https://oauth.vk.com/authorize?" +
            "response_type=code" +
            "&client_id=$clientId" +
            "&redirect_uri=$redirectUrl" +
            if (!TextUtils.isEmpty(scope)) "&scope=$scope" else ""
}