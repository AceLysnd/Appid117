package com.qatros.logibug.core.data.request.ai

import com.google.gson.annotations.SerializedName

data class AICommandRequest(
    @SerializedName("data")
    val data: String
)