package com.qatros.logibug.core.data.response.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetProjectByIdResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProjectByIdDetail
): Parcelable

@Parcelize
data class ProjectByIdDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type_test")
    val typeTest: String,
    @SerializedName("platform")
    val platform: String
): Parcelable