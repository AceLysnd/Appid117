package com.qatros.logibug.core.data.response.delete_account

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeleteUserResponse(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("data")
    val data: DeleteUserData
): Parcelable

@Parcelize
data class DeleteUserData(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String
): Parcelable
