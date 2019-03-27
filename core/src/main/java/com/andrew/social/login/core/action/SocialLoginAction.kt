package com.andrew.social.login.core.action

import android.app.Activity
import android.content.Intent

/**
 * Created by Andrew on 16.06.2018.
 */

abstract class SocialLoginAction(val activity: Activity) {

    var callback: SocialLoginCallback? = null

    abstract fun login()

    open fun logout() {}

    abstract fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?)
}