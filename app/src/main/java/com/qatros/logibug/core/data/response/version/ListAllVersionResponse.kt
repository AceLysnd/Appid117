package com.qatros.logibug.core.data.response.version

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListAllVersionResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<AllVersionResponse>
): Parcelable

@Parcelize
data class AllVersionResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("project_id")
    val projectId: Int
): Parcelable

