package com.qatros.logibug.core.data.response.scenario

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateScenarioResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailCreateScenario
): Parcelable

@Parcelize
data class DetailCreateScenario(
    @SerializedName("id")
    val scenarioId: Int,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("name")
    val name: String
): Parcelable