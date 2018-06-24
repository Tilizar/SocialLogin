package com.andrew.socialactionssample.data.social.instagram.request

import com.andrew.socialactionssample.data.social.instagram.exception.InstagramGetUserException
import com.andrew.socialactionssample.data.social.instagram.misc.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramGetUserRequest {

    fun getUser(token: String): Single<String> =
            Single.fromCallable { manageConnection(token) }
                    .subscribeOn(Schedulers.newThread())
                    .doOnSuccess { checkHttpCode(it, token) }
                    .map { parseUserName(it) }

    private fun manageConnection(token: String): HttpURLConnection {
        val url = URL(SELF_INFO_URL + ACCESS_TOKEN_TYPE_DEF + token)
        val conn = url.openConnection() as HttpURLConnection

        conn.requestMethod = GET_METHOD
        conn.doInput = true
        conn.connect()

        return conn
    }

    private fun checkHttpCode(conn: HttpURLConnection, token: String) {
        if (conn.responseCode != HttpURLConnection.HTTP_OK) {
            throw InstagramGetUserException(token)
        }
    }

    private fun parseUserName(conn: HttpURLConnection): String {
        val br = BufferedReader(InputStreamReader(conn.inputStream))
        val sb = StringBuilder()
        var line: String?
        do {
            line = br.readLine()
            line?.let { sb.append(it + "\n") }
        } while (line != null)
        br.close()

        return Gson().fromJson<JsonObject>(sb.toString(), JsonObject::class.java)
                .getAsJsonObject(PARSE_USER_DATA)[PARSE_USER_NAME].asString
    }
}