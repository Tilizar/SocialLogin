package com.andrew.socialactionssample.data.social.google

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.data.social.SocialType
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.google.exception.PlayServicesNotInstalledException
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient

/**
 * Created by Andrew on 28.06.2018
 */

class GoogleLoginActionImpl(activity: AppCompatActivity,
                            private val clientId: String? = null) : SocialLoginAction(activity) {

    companion object {
        private const val REQUEST_CODE_GOOGLE = 12345
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
            result.signInAccount?.let {
                callback?.onSuccess(SocialType.GOOGLE,
                        it.idToken ?: it.id ?: "",
                        it.displayName ?: "")
            }
        }
    }

    override fun cancelRequest() {

    }

    private fun initGoogleApiClient() {
        if (googleApi != null) return

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()

        if (clientId != null) {
            options.requestIdToken(clientId)
        } else {
            options.requestId()
        }

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