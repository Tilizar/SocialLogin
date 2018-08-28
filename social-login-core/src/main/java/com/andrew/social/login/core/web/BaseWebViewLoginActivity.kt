package com.andrew.social.login.core.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_CODE
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_RESPONSE_TYPE
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_SOCIAL_TYPE
import com.andrew.social.login.core.web.WebActivityStarter.BUNDLE_URL

/**
 * Created by Andrey Matyushin on 28.08.2018
 */

abstract class BaseWebViewLoginActivity : Activity() {

    @LayoutRes abstract fun layoutResId(): Int

    abstract fun webView(): WebView

    abstract fun showLoading()

    abstract fun hideLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView().apply {
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

    fun finishWithSuccess(code: String) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(BUNDLE_CODE, code) })
        finish()
    }

    fun finishWithError() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    inner class LoginWebViewClient : CustomWebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            request?.let {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    handleUrl(it.url.toString())
                } else {
                    false
                }
            }
            return false
        }
    }

    inner class LoginWebViewClientBelowLollipop : CustomWebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return if (url != null) handleUrl(url) else false
        }
    }

    abstract inner class CustomWebViewClient : WebViewClient() {
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            hideLoading()
            finishWithError()
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoading()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            hideLoading()
        }
    }

    fun handleUrl(url: String?): Boolean {
        val socialType = intent.extras.getSerializable(BUNDLE_SOCIAL_TYPE) as SocialType?
        val responseType = intent.extras.getSerializable(BUNDLE_RESPONSE_TYPE) as ResponseType?
        if (socialType == null || responseType == null) {
            return UrlHandler.DEFAULT_ACTION.invoke(url, this);
        }
        return UrlHandler.getHandler(socialType, responseType)?.invoke(url, this) ?: UrlHandler.DEFAULT_ACTION.invoke(url, this);
    }
}