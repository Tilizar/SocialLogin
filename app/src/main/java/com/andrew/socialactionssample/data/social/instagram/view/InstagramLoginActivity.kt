package com.andrew.socialactionssample.data.social.instagram.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.data.social.instagram.exception.InstagramGetTokenException
import com.andrew.socialactionssample.data.social.instagram.misc.ACCESS_TOKEN
import com.andrew.socialactionssample.data.social.instagram.misc.EQUAL
import com.andrew.socialactionssample.data.social.instagram.misc.ERROR
import com.andrew.socialactionssample.data.social.instagram.misc.InstagramResponse
import com.andrew.socialactionssample.data.social.instagram.request.InstagramGetUserRequest
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
            webViewClient = LoginWebViewClient()
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

    private inner class LoginWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            request?.let {
                val url = it.url.toString()
                if (url.startsWith(redirectUrl)) {
                    if (url.contains(ACCESS_TOKEN)) {
                        val tokenArray = url.split(EQUAL.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (tokenArray.size > 1) {
                            token = tokenArray[1]
                            getUserDisposable = this@InstagramLoginActivity.request.getUser(token)
                                    .subscribe({ finishWithSuccess(token, it) }, { finishWithError(it) })
                        } else {
                            finishWithError(InstagramGetTokenException())
                        }
                    } else if (url.contains(ERROR)) {
                        finishWithError(InstagramGetTokenException())
                    }
                    return true
                }
            }
            return false
        }

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
}