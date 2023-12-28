package com.qatros.logibug.core.data.response.member

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListAllMemberResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<DetailMember>
) : Parcelable

@Parcelize
data class DetailMember(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable