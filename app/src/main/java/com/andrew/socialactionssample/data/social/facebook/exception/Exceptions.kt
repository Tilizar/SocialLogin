package com.andrew.socialactionssample.data.social.facebook.exception

/**
 * Created by Andrew on 24.06.2018
 */

class FacebookError : Exception {
    constructor() : super()
    constructor(msg: String?) : super(msg)
}

class FacebookCancelError : Exception()