package com.qatros.logibug.core.data.response.project

import com.google.gson.annotations.SerializedName

data class ListProjectResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ProjectResponse>
)
