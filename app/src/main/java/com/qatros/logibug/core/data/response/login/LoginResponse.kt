package com.qatros.logibug.core.data.response.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("access_token")
    val token: String,
    @field:SerializedName("refres_token")
    val refreshToken: String,
    @field:SerializedName("exp")
    val exp: Int,
    @field:SerializedName("data")
    val data: Data
) : Parcelable

@Parcelize
data class Data(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String
) : Parcelable