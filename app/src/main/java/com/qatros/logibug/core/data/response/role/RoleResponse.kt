package com.qatros.logibug.core.data.response.role

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoleResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailRole
): Parcelable

@Parcelize
data class DetailRole(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String
): Parcelable
