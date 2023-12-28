package com.qatros.logibug.core.data.response.member

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InviteMemberResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailInviteMember
): Parcelable

@Parcelize
data class DetailInviteMember(
    @SerializedName("id")
    val idMember: Int,
    @SerializedName("email")
    val emailMember: String
): Parcelable
