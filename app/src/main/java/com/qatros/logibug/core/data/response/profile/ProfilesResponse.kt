package com.qatros.logibug.core.data.response.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profiles(
    @SerializedName("data")
    val data: ProfilesDetail
) : Parcelable

@Parcelize
data class ProfilesDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("img_url")
    val imageProfile: String
) : Parcelable