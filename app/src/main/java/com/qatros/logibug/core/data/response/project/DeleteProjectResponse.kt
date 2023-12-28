package com.qatros.logibug.core.data.response.project

import com.google.gson.annotations.SerializedName

data class DeleteProjectResponse(
    @SerializedName("message")
    val message: String
)