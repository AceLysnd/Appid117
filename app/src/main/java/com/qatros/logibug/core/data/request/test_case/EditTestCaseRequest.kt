package com.qatros.logibug.core.data.request.test_case

import com.google.gson.annotations.SerializedName

data class EditTestCaseRequest(
    @SerializedName("test_category")
    val testCategory: String,
    @SerializedName("pre_condition")
    val preCondition: String,
    @SerializedName("testcase")
    val testCaseName: String,
    @SerializedName("test_step")
    val testStep: String,
    @SerializedName("expectation")
    val expectation: String
)