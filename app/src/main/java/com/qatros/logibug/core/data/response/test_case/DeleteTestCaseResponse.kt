package com.qatros.logibug.core.data.response.test_case

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteTestCaseResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailDeleteTestCase
): Parcelable

@Parcelize
data class DetailDeleteTestCase(
    @SerializedName("id")
    val testCaseId: Int,
    @SerializedName("version_id")
    val versionId: Int,
    @SerializedName("scenario_id")
    val scenarioId: Int,
    @SerializedName("scenario_name")
    val scenarioName: String,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("test_category")
    val testCaseCategory: String,
    @SerializedName("pre_condition")
    val preCondition: String,
    @SerializedName("testcase")
    val testCaseName: String,
    @SerializedName("test_step")
    val testStep: String,
    @SerializedName("expectation")
    val expectation: String,
    @SerializedName("status")
    val status: Boolean?
): Parcelable