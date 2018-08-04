package com.andrew.socialactionssample.presentation.feature.main.view

import com.andrew.social.login.core.SocialType
import com.andrew.socialactionssample.presentation.feature.base.view.BaseView

/**
 * Created by Andrew on 16.06.2018.
 */

interface MainView : BaseView {
    fun updateToken(socialType: SocialType, code: String)
}