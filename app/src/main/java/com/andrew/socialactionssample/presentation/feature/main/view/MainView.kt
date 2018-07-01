package com.andrew.socialactionssample.presentation.feature.main.view

import com.andrew.socialactionssample.presentation.feature.base.view.BaseView
import com.andrew.social.login.core.SocialType

/**
 * Created by Andrew on 16.06.2018.
 */

interface MainView : BaseView {
    fun updateToken(socialType: SocialType, token: String, info: String)
}