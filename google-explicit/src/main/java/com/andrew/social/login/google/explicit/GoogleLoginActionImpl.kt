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
import com.google.android.gms.common.api.Scope

/**
 * Created by Andrew on 28.06.2018
 */

class GoogleLoginActionImpl(
    activity: Activity,
    private val clientId: String,
    private val scopes: List<AuthScope> = emptyList()
) : SocialLoginAction(activity) {

    companion object {
        private const val REQUEST_CODE_GOOGLE = 10003
    }

    private var googleApi: GoogleApiClient? = null

    override fun login() {
        initGoogleApiClient()

        googleApi?.let {
            if (!it.isConnected) it.connect()
        }

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
                    googleApi = null
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
            .requestEmail()
            .addScopes()
            .requestIdToken(clientId)

        googleApi = GoogleApiClient.Builder(activity)
            .addApi(Auth.GOOGLE_SIGN_IN_API, options.build())
            .build()
    }

    private fun GoogleSignInOptions.Builder.addScopes(): GoogleSignInOptions.Builder {
        scopes.forEach { requestScopes(Scope(it.scope)) }
        return this
    }

    private fun checkPlayServices(): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(activity)
        return result == ConnectionResult.SUCCESS
    }
}

enum class AuthScope(val scope: String) {
    PROFILE("profile"),
    EMAIL("email"),
    OPEN_ID("openid"),

    @Deprecated("")
    PLUS_LOGIN("https://www.googleapis.com/auth/plus.login"),
    PLUS_ME("https://www.googleapis.com/auth/plus.me"),
    GAMES("https://www.googleapis.com/auth/games"),
    GAMES_LITE("https://www.googleapis.com/auth/games_lite"),
    CLOUD_SAVE("https://www.googleapis.com/auth/datastoremobile"),
    APP_STATE("https://www.googleapis.com/auth/appstate"),
    DRIVE_FILE("https://www.googleapis.com/auth/drive.file"),
    DRIVE_APPFOLDER("https://www.googleapis.com/auth/drive.appdata"),
    DRIVE_FULL("https://www.googleapis.com/auth/drive"),
    DRIVE_APPS("https://www.googleapis.com/auth/drive.apps"),
    FITNESS_ACTIVITY_READ("https://www.googleapis.com/auth/fitness.activity.read"),
    FITNESS_ACTIVITY_READ_WRITE("https://www.googleapis.com/auth/fitness.activity.write"),
    FITNESS_LOCATION_READ("https://www.googleapis.com/auth/fitness.location.read"),
    FITNESS_LOCATION_READ_WRITE("https://www.googleapis.com/auth/fitness.location.write"),
    FITNESS_BODY_READ("https://www.googleapis.com/auth/fitness.body.read"),
    FITNESS_BODY_READ_WRITE("https://www.googleapis.com/auth/fitness.body.write"),
    FITNESS_NUTRITION_READ("https://www.googleapis.com/auth/fitness.nutrition.read"),
    FITNESS_NUTRITION_READ_WRITE("https://www.googleapis.com/auth/fitness.nutrition.write"),
    FITNESS_BLOOD_PRESSURE_READ("https://www.googleapis.com/auth/fitness.blood_pressure.read"),
    FITNESS_BLOOD_PRESSURE_READ_WRITE("https://www.googleapis.com/auth/fitness.blood_pressure.write"),
    FITNESS_BLOOD_GLUCOSE_READ("https://www.googleapis.com/auth/fitness.blood_glucose.read"),
    FITNESS_BLOOD_GLUCOSE_READ_WRITE("https://www.googleapis.com/auth/fitness.blood_glucose.write"),
    FITNESS_OXYGEN_SATURATION_READ("https://www.googleapis.com/auth/fitness.oxygen_saturation.read"),
    FITNESS_OXYGEN_SATURATION_READ_WRITE("https://www.googleapis.com/auth/fitness.oxygen_saturation.write"),
    FITNESS_BODY_TEMPERATURE_READ("https://www.googleapis.com/auth/fitness.body_temperature.read"),
    FITNESS_BODY_TEMPERATURE_READ_WRITE("https://www.googleapis.com/auth/fitness.body_temperature.write"),
    FITNESS_REPRODUCTIVE_HEALTH_READ("https://www.googleapis.com/auth/fitness.reproductive_health.read"),
    FITNESS_REPRODUCTIVE_HEALTH_READ_WRITE("https://www.googleapis.com/auth/fitness.reproductive_health.write")
}