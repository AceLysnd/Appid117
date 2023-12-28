package com.qatros.logibug.core.data.response.api_testing

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiTestingResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailApiTesting
): Parcelable

@Parcelize
data class DetailApiTesting(
    @SerializedName("name")
    val name: String,
    @SerializedName("version_id")
    val versionId: Int
): Parcelable