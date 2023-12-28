package com.qatros.logibug.core.data.response.scenario

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListScenarioResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ScenarioDetail>
): Parcelable

@Parcelize
data class ScenarioDetail(
    @SerializedName("id")
    val scenarioId: Int,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("name")
    val name: String
): Parcelable