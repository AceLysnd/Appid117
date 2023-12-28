package com.qatros.logibug.core.data.response.test_case

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterTestCaseResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<DetailFilterTestCase>
): Parcelable

@Parcelize
data class DetailFilterTestCase(
    @SerializedName("id")
    val id: Int,
    @SerializedName("version_id")
    val versionId: Int,
    @SerializedName("scenario_id")
    val scenarioId: Int,
    @SerializedName("scenario_name")
    val scenarioName: String,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("test_category")
    val testCategory: String,
    @SerializedName("pre_condition")
    val preCondition: String,
    @SerializedName("testcase")
    val testCase: String,
    @SerializedName("test_step")
    val testStep: String,
    @SerializedName("expectation")
    val expectation: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("severity")
    val severity: String,
    @SerializedName("priority")
    val priority: String
): Parcelable