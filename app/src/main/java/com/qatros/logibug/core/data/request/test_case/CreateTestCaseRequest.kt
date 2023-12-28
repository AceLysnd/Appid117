package com.qatros.logibug.core.data.request.test_case

import com.google.gson.annotations.SerializedName

data class CreateTestCaseRequest(
    @SerializedName("test_category")
    val testCaseCategory: String,
    @SerializedName("scenario_id")
    val scenarioId: Int,
    @SerializedName("version_id")
    val versionId: Int,
    @SerializedName("pre_condition")
    val preCondition: String,
    @SerializedName("testcase")
    val testCase: String,
    @SerializedName("test_step")
    val testStep: String,
    @SerializedName("expectation")
    val expectation: String
)
