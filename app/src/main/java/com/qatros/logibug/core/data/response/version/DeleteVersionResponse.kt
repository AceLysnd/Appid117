package com.qatros.logibug.core.data.response.version

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteVersionResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailDeleteVersion
): Parcelable

@Parcelize
data class DetailDeleteVersion(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("project_id")
    val projectId: Int
): Parcelable