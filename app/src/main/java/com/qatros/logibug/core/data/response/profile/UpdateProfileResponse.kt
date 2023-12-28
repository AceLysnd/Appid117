package com.qatros.logibug.core.data.response.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateProfileResponse(
    @SerializedName("data")
    val data: ProfilesUpdateDetail
) : Parcelable

@Parcelize
data class ProfilesUpdateDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("img_url")
    val imageProfile: String
) : Parcelable