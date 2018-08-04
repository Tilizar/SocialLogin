package com.andrew.social.login.core.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.andrew.social.login.core.R
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.exception.LoginException
import kotlinx.android.synthetic.main.activity_webview_login.*

/**
 * Created by Andrew on 16.07.2018
 */

class WebViewLoginActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_SOCIAL = "BUNDLE_SOCIAL"
        private const val BUNDLE_URL = "BUNDLE_URL"

        const val BUNDLE_CODE = "BUNDLE_CODE"
        const val BUNDLE_EXCEPTION = "BUNDLE_EXCEPTION"

        private const val QUERY_PARAMETER_CODE = "code"

        @JvmStatic
        fun openLoginActivity(activity: AppCompatActivity,
                              requestCode: Int,
                              social: SocialType,
                              url: String) {
            activity.startActivityForResult(Intent(activity, WebViewLoginActivity::class.java).apply {
                putExtra(BUNDLE_SOCIAL, social.name)
                putExtra(BUNDLE_URL, url)
            }, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_login)
        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webview.apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            webViewClient = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                LoginWebViewClient()
            } else {
                LoginWebViewClientBelowLollipop()
            }
            settings.javaScriptEnabled = true
            loadUrl(intent.extras.getString(BUNDLE_URL))
        }
    }

    private fun finishWithSuccess(code: String) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(BUNDLE_CODE, code) })
        finish()
    }

    private fun finishWithError(error: Throwable) {
        setResult(Activity.RESULT_CANCELED, Intent().apply { putExtra(BUNDLE_EXCEPTION, error) })
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private inner class LoginWebViewClient : CustomWebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            request?.let {
                val url = it.url.toString()
                return handleUrl(url)
            }
            return false
        }
    }

    private inner class LoginWebViewClientBelowLollipop : CustomWebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return if (url != null) handleUrl(url) else false
        }
    }

    private abstract inner class CustomWebViewClient : WebViewClient() {
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            progress.visibility = View.GONE
            finishWithError(LoginException(SocialType.valueOf(intent.extras.getString(BUNDLE_SOCIAL))))
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progress.visibility = View.GONE
        }
    }

    private fun handleUrl(url: String?): Boolean {
        val uri = Uri.parse(url)
        return if (uri.queryParameterNames.contains(QUERY_PARAMETER_CODE)) {
            finishWithSuccess(uri.getQueryParameter(QUERY_PARAMETER_CODE))
            true
        } else {
            webview.loadUrl(url)
            false
        }
    }
}