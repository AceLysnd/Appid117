package com.qatros.logibug.core.data.response.version

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateVersionResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailVersion
): Parcelable

@Parcelize
data class DetailVersion(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("project_id")
    val projectId: Int
): Parcelable