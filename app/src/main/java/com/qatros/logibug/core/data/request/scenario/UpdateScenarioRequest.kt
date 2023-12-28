package com.qatros.logibug.core.data.request.scenario

import com.google.gson.annotations.SerializedName

data class UpdateScenarioRequest(
    @SerializedName("name")
    val name: String
)