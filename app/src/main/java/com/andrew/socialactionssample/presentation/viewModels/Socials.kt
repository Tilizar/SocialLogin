package com.andrew.socialactionssample.presentation.viewModels

import android.support.annotation.DrawableRes
import com.andrew.social.login.core.SocialType
import com.andrew.socialactionssample.R

/**
 * Created by Andrew on 17.06.2018.
 */

data class SocialModel(var socialType: SocialType,
                       var socialViewType: SocialViewType? = SocialViewType.getSocialViewType(socialType),
                       var code: String = "")

enum class SocialViewType(var socialType: SocialType,
                          @DrawableRes var drawableRes: Int) {
    VK(SocialType.VKONTAKTE, R.drawable.logo_vk),
    TWITTER(SocialType.TWITTER, R.drawable.logo_twitter),
    INSTAGRAM(SocialType.INSTAGRAM, R.drawable.logo_instagram),
    FACEBOOK(SocialType.FACEBOOK, R.drawable.logo_facebook),
    GOOGLE(SocialType.GOOGLE, R.drawable.logo_google),
    LINKED_IN(SocialType.LINKED_IN, R.drawable.logo_linked_in);

    companion object {
        fun getSocialViewType(socialType: SocialType) = values().firstOrNull { it.socialType == socialType }
    }
}