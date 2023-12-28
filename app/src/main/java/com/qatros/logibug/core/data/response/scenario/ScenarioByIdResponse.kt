package com.qatros.logibug.core.data.response.scenario

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScenarioByIdResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailScenario
): Parcelable

@Parcelize
data class DetailScenario(
    @SerializedName("id")
    val scenarioId: Int,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("name")
    val scenarioName: String
): Parcelable