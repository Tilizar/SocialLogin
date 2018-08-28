package com.andrew.social.login.core.web

import android.view.View
import android.webkit.WebView
import com.andrew.social.login.core.R
import kotlinx.android.synthetic.main.activity_webview_login.*

/**
 * Created by Andrew on 16.07.2018
 */

class WebViewLoginActivity : BaseWebViewLoginActivity() {

    override fun layoutResId(): Int = R.layout.activity_webview_login

    override fun webView(): WebView = webview

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.INVISIBLE
    }
}