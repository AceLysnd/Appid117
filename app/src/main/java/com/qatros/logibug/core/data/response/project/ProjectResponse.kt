package com.qatros.logibug.core.data.response.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val projectName: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("type_test")
    val typeTest: String,
    @SerializedName("platform")
    val platform: String
) : Parcelable