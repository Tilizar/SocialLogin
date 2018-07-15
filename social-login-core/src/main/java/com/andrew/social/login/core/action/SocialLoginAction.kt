package com.andrew.social.login.core.action

import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by Andrew on 16.06.2018.
 */

abstract class SocialLoginAction(val activity: AppCompatActivity) {

    var callback: SocialLoginManager.LoginCallback? = null

    abstract fun login()

    abstract fun logout()

    abstract fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?)

    abstract fun cancelRequest()
}