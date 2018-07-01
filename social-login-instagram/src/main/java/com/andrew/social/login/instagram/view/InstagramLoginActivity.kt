package com.andrew.social.login.instagram.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.andrew.social.login.instagram.R
import com.andrew.social.login.instagram.exception.InstagramGetTokenException
import com.andrew.social.login.instagram.misc.ACCESS_TOKEN
import com.andrew.social.login.instagram.misc.EQUAL
import com.andrew.social.login.instagram.misc.ERROR
import com.andrew.social.login.instagram.misc.InstagramResponse
import com.andrew.social.login.instagram.request.InstagramGetUserRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_instagram_login.*

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramLoginActivity : AppCompatActivity() {

    companion object {
        internal const val BUNDLE_AUTH_URL = "BUNDLE_AUTH_URL"
        internal const val BUNDLE_REDIRECT_URL = "BUNDLE_REDIRECT_URL"
        internal const val BUNDLE_RESPONSE = "BUNDLE_RESPONSE"
        internal const val BUNDLE_EXCEPTION = "BUNDLE_EXCEPTION"
    }

    private val request = InstagramGetUserRequest()

    private var authUrl = ""
    private var redirectUrl = ""

    private var token = ""

    private var getUserDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            authUrl = it.getString(BUNDLE_AUTH_URL, "")
            redirectUrl = it.getString(BUNDLE_REDIRECT_URL, "")
        }
        setContentView(R.layout.activity_instagram_login)
        setUpWebView()
    }

    override fun onStop() {
        super.onStop()
        getUserDisposable = null
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webview.apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webViewClient = LoginWebViewClient()
            } else {
                webViewClient = LoginWebViewClientBelowLollipop()
            }
            settings.javaScriptEnabled = true
            loadUrl(authUrl)
        }
    }

    private fun finishWithSuccess(token: String, userName: String) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(BUNDLE_RESPONSE, InstagramResponse(token, userName)) })
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
            finishWithError(InstagramGetTokenException())
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

    private fun handleUrl(url: String): Boolean {
        return if (url.startsWith(redirectUrl)) {
            if (url.contains(ACCESS_TOKEN)) {
                val tokenArray = url.split(EQUAL.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (tokenArray.size > 1) {
                    token = tokenArray[1]
                    getUserDisposable = this@InstagramLoginActivity.request.getUser(token)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ finishWithSuccess(token, it) }, { finishWithError(it) })
                } else {
                    finishWithError(InstagramGetTokenException())
                }
            } else if (url.contains(ERROR)) {
                finishWithError(InstagramGetTokenException())
            }
            true
        } else false
    }
}