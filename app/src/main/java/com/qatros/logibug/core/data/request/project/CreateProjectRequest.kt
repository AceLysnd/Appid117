package com.qatros.logibug.core.data.request.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateProjectRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("type_test")
    val typeTest: String,
    @SerializedName("platform")
    val platform: String
) : Parcelable

