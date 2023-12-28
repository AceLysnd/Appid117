package com.qatros.logibug.core.data.request.member

import com.google.gson.annotations.SerializedName

data class InviteMemberRequest(
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String
)