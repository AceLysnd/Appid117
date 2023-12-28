package com.qatros.logibug.core.data.response.api_testing

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RunDataJsonResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailRunDataJson
): Parcelable

@Parcelize
data class DetailRunDataJson(
    @SerializedName("id")
    val dataId: Int,
    @SerializedName("version_id")
    val versionId: Int,
    @SerializedName("method")
    val method: String,
    @SerializedName("folder_name")
    val folderName: String,
    @SerializedName("req_name")
    val reqName: String,
    @SerializedName("folder")
    val folder: Boolean,
    @SerializedName("response")
    val response: ResultRunDataJsonResponse
): Parcelable

@Parcelize
data class ResultRunDataJsonResponse(
    @SerializedName("code")
    val code: String
): Parcelable