package com.andrew.socialactionssample.presentation.viewModels

import android.support.annotation.DrawableRes
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.data.social.qualifier.SocialType

/**
 * Created by Andrew on 17.06.2018.
 */

data class SocialModel(var socialType: SocialType,
                       var socialViewType: SocialViewType = SocialViewType.getSocialViewType(socialType),
                       var info: String = "",
                       var token: String = "")

enum class SocialViewType(var socialType: SocialType,
                          @DrawableRes var drawableRes: Int) {
    VK(SocialType.VK, R.drawable.logo_vk);

    companion object {
        fun getSocialViewType(socialType: SocialType) = values().first { it.socialType == socialType }
    }
}