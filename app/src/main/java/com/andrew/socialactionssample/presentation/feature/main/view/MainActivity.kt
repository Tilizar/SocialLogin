package com.andrew.socialactionssample.presentation.feature.main.view

import android.content.Intent
import android.os.Bundle
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.data.social.SocialLoginManager
import com.andrew.socialactionssample.presentation.feature.base.view.BaseActivity
import com.andrew.socialactionssample.presentation.feature.base.view.Layout
import com.andrew.socialactionssample.presentation.feature.main.presenter.MainPresenter
import javax.inject.Inject

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity<MainPresenter, MainView>() {

    @Inject
    lateinit var loginManager: SocialLoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginManager.observeLoginCallback(presenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginManager.disposeLoginCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginManager.handleResult(requestCode, resultCode, data)
    }
}
