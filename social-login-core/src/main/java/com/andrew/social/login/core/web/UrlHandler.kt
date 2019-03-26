package com.andrew.social.login.core.web

import android.net.Uri
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import kotlinx.android.synthetic.main.activity_webview_login.*

/**
 * Created by Andrey Matyushin on 28.08.2018
 */

object UrlHandler {

    private const val QUERY_CODE = "code"

    private val handlers: MutableMap<String, (String?, BaseWebViewLoginActivity) -> Boolean> = mutableMapOf()

    val DEFAULT_ACTION: (String?, BaseWebViewLoginActivity) -> Boolean = { url, activity ->
        val uri = Uri.parse(url)
        if (uri.queryParameterNames.contains(QUERY_CODE)) {
            activity.finishWithSuccess(uri.getQueryParameter(QUERY_CODE) ?: "")
            true
        } else {
            activity.webview.loadUrl(url)
            false
        }
    }

    fun getHandler(socialType: SocialType, responseType: ResponseType): ((String?, BaseWebViewLoginActivity) -> Boolean)? = handlers[parseKey(socialType, responseType)]

    fun putHandler(socialType: SocialType, responseType: ResponseType, handler: (String?, BaseWebViewLoginActivity) -> Boolean) {
        handlers[parseKey(socialType, responseType)] = handler
    }

    private fun parseKey(socialType: SocialType, responseType: ResponseType): String = socialType.name + "_" + responseType.name
}