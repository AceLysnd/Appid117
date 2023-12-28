package com.qatros.logibug.core.data.response.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
    @field:SerializedName("message")
    val message: String,
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