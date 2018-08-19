package com.andrew.social.login.instagram.implicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.view.WebViewLoginActivity

/**
 * Created by Andrew on 16.07.2018
 */

class InstagramLoginActivity : WebViewLoginActivity() {

    companion object {
        private const val BUNDLE_REDIRECT_URL = "BUNDLE_REDIRECT_URL"

        const val RESPONSE_TOKEN = "access_token"
        const val RESPONSE_ERROR = "error"

        @JvmStatic
        fun openInstagramLoginActivity(activity: Activity,
                              requestCode: Int,
                              url: String,
                              redirectUrl: String) {
            activity.startActivityForResult(Intent(activity, InstagramLoginActivity::class.java).apply {
                putExtra(BUNDLE_URL, url)
                putExtra(BUNDLE_REDIRECT_URL, redirectUrl)
            }, requestCode)
        }
    }

    override fun handleUrl(url: String?): Boolean {
        val redirectUrl = intent.extras?.getString(BUNDLE_REDIRECT_URL)
        if (url == null || redirectUrl == null) return false
        return if (url.startsWith(redirectUrl)) {
            if (url.contains(RESPONSE_TOKEN)) {
                val tokenArray = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (tokenArray.size > 1) {
                    finishWithSuccess(tokenArray[1])
                } else {
                    finishWithError()
                }
            } else if (url.contains(RESPONSE_ERROR)) {
                finishWithError()
            }
            true
        } else false
    }
}