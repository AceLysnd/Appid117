package com.qatros.logibug.core.data.response.api_testing

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListDataJsonResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<DetailDataJson>
) : Parcelable

@Parcelize
data class DetailDataJson(
    @SerializedName("id")
    val dataJsonId: Int,
    @SerializedName("version_id")
    val versionId: Int,
    @SerializedName("method")
    val method: String,
    @SerializedName("folder_name")
    val folderName: String,
    @SerializedName("req_name")
    val requestName: String,
    @SerializedName("folder")
    val folder: Boolean
): Parcelable