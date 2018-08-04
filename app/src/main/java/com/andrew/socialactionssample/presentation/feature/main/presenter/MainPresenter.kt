package com.andrew.socialactionssample.presentation.feature.main.presenter

import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginManager
import com.andrew.socialactionssample.di.PerActivity
import com.andrew.socialactionssample.presentation.feature.main.view.MainView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

/**
 * Created by Andrew on 16.06.2018.
 */

@PerActivity
@InjectViewState
class MainPresenter @Inject constructor() : MvpPresenter<MainView>(),
        SocialLoginManager.LoginCallback {

    override fun onSuccess(socialType: SocialType, code: String) {
        viewState.updateToken(socialType, code)
    }

    override fun onError(error: Throwable) {
        error.printStackTrace()
    }
}