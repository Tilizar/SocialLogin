package com.andrew.socialactionssample.data.social.instagram.misc

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Andrew on 24.06.2018
 */

data class InstagramResponse(val token: String, val userName: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InstagramResponse> {
        override fun createFromParcel(parcel: Parcel): InstagramResponse {
            return InstagramResponse(parcel)
        }

        override fun newArray(size: Int): Array<InstagramResponse?> {
            return arrayOfNulls(size)
        }
    }
}