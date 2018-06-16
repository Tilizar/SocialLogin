package com.andrew.socialactionssample.presentation.feature.main.presenter

import android.util.Log
import com.andrew.socialactionssample.data.social.SocialLoginManager
import com.andrew.socialactionssample.data.social.qualifier.SocialType
import com.andrew.socialactionssample.di.PerActivity
import com.andrew.socialactionssample.presentation.feature.base.presenter.BasePresenter
import com.andrew.socialactionssample.presentation.feature.main.view.MainView
import javax.inject.Inject

/**
 * Created by Andrew on 16.06.2018.
 */

@PerActivity
class MainPresenter @Inject constructor() : BasePresenter<MainView>(),
        SocialLoginManager.LoginCallback {

    override fun onSuccess(socialType: SocialType, token: String) {
        Log.d("SOCIAL", token)
    }

    override fun onError(error: Throwable) {
        Log.d("SOCIAL", error.message)
    }
}