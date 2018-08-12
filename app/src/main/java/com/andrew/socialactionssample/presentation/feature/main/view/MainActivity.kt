package com.andrew.socialactionssample.presentation.feature.main.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginManager
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.presentation.feature.base.view.BaseActivity
import com.andrew.socialactionssample.presentation.feature.base.view.Layout
import com.andrew.socialactionssample.presentation.feature.main.adapter.SocialsAdapter
import com.andrew.socialactionssample.presentation.feature.main.presenter.MainPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by Andrew on 16.06.2018.
 */

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity(), MainView, SocialsAdapter.SocialClickListener {

    @Inject
    lateinit var loginManager: SocialLoginManager

    @Inject
    lateinit var adapter: SocialsAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginManager.observeLoginCallback(presenter)
        setupRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginManager.disposeLoginCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginManager.handleResult(requestCode, resultCode, data)
    }

    override fun updateToken(socialType: SocialType, code: String) {
        adapter.updateSocial(socialType, code)
    }

    override fun loginClick(socialType: SocialType) {
        loginManager.login(socialType)
    }

    override fun logoutClick(socialType: SocialType) {
        loginManager.logout()
        adapter.updateSocial(socialType, "")
    }

    private fun setupRecycler() {
        recycler_socials.apply {
            layoutManager = this@MainActivity.layoutManager
            adapter = this@MainActivity.adapter
            if (itemAnimator is SimpleItemAnimator) {
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }
    }
}
