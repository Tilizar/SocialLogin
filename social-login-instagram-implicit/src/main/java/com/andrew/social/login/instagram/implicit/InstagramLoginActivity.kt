package com.andrew.social.login.instagram.implicit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.andrew.social.login.core.R
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.exception.SocialLoginException
import com.andrew.social.login.core.view.WebViewLoginActivity.Companion.BUNDLE_CODE

/**
 * Created by Andrew on 16.07.2018
 */

class InstagramLoginActivity : AppCompatActivity() {

    val webview: WebView by lazy { findViewById<WebView>(R.id.webview) }
    val progress: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress) }

    companion object {
        private const val BUNDLE_URL = "BUNDLE_URL"
        private const val BUNDLE_REDIRECT_URL = "BUNDLE_REDIRECT_URL"

        const val BUNDLE_CODE = "BUNDLE_CODE"
        const val BUNDLE_EXCEPTION = "BUNDLE_EXCEPTION"

        const val RESPONSE_TOKEN = "access_token"
        const val RESPONSE_ERROR = "error"

        @JvmStatic
        fun openInstagramLoginActivity(activity: AppCompatActivity,
                              requestCode: Int,
                              url: String,
                              redirectUrl: String) {
            activity.startActivityForResult(Intent(activity, InstagramLoginActivity::class.java).apply {
                putExtra(BUNDLE_URL, url)
                putExtra(BUNDLE_REDIRECT_URL, redirectUrl)
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

    private inner class LoginWebViewClient : CustomWebViewClient() {
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

    private inner class LoginWebViewClientBelowLollipop : CustomWebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return if (url != null) handleUrl(url) else false
        }
    }

    private abstract inner class CustomWebViewClient : WebViewClient() {
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            progress.visibility = View.GONE
            finishWithError(SocialLoginException(SocialType.INSTAGRAM))
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
        val redirectUrl = intent.extras?.getString(BUNDLE_REDIRECT_URL)
        if (url == null || redirectUrl == null) return false
        return if (url.startsWith(redirectUrl)) {
            if (url.contains(RESPONSE_TOKEN)) {
                val tokenArray = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (tokenArray.size > 1) {
                    finishWithSuccess(tokenArray[1])
                } else {
                    finishWithError(SocialLoginException(SocialType.INSTAGRAM))
                }
            } else if (url.contains(RESPONSE_ERROR)) {
                finishWithError(SocialLoginException(SocialType.INSTAGRAM))
            }
            true
        } else false
    }
}