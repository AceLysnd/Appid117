package com.qatros.logibug.core.data.response.version

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetVersionByIdResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<VersionByIdDetail>
): Parcelable

@Parcelize
data class VersionByIdDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
): Parcelable