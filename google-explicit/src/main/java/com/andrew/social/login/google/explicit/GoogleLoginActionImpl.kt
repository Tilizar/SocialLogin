package com.andrew.social.login.google.explicit

import android.app.Activity
import android.content.Intent
import com.andrew.social.login.core.ResponseType
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.exception.SocialLoginException
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient

/**
 * Created by Andrew on 28.06.2018
 */

class GoogleLoginActionImpl(activity: Activity,
                            private val clientId: String) : SocialLoginAction(activity) {

    companion object {
        private const val REQUEST_CODE_GOOGLE = 10003
    }

    private var googleApi: GoogleApiClient? = null

    override fun login() {
        initGoogleApiClient()

        if (!checkPlayServices()) {
            callback?.onError(PlayServicesNotInstalledException())
        }

        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApi)
        activity.startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE)
    }

    override fun logout() {
        googleApi?.apply {
            if (!isConnected) return

            Auth.GoogleSignInApi.signOut(googleApi)
                    .setResultCallback {
                        googleApi?.disconnect()
                    }
        }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != REQUEST_CODE_GOOGLE) return
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
        if (result.isSuccess) {
            result.signInAccount?.idToken?.let {
                callback?.onSuccess(SocialType.GOOGLE, ResponseType.SERVER_CODE, it)
            } ?: throwLoginError()
        } else throwLoginError()
    }

    private fun throwLoginError() {
        callback?.onError(SocialLoginException(SocialType.GOOGLE))
    }

    override fun cancelRequest() {
        googleApi?.disconnect()
    }

    private fun initGoogleApiClient() {
        if (googleApi != null) return

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestIdToken(clientId)

        googleApi = GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options.build())
                .build()

        googleApi?.connect()
    }

    private fun checkPlayServices(): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(activity)
        return result == ConnectionResult.SUCCESS
    }
}