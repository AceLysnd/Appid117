package com.qatros.logibug.core.data.request.scenario

import com.google.gson.annotations.SerializedName

data class CreateScenarioRequest(
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("name")
    val scenarioName: String
)