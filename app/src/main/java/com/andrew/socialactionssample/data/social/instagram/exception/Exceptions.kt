package com.andrew.socialactionssample.data.social.instagram.exception

/**
 * Created by Andrew on 24.06.2018
 */

class InstagramGetUserException(private var token: String) : Exception()

class InstagramGetTokenException : Exception()