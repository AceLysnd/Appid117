package com.qatros.logibug.core.data.response.test_case

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateTestCaseResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DetailCreateTestCase
): Parcelable

@Parcelize
data class DetailCreateTestCase(
        @SerializedName("id")
        val testCaseId: Int,
        @SerializedName("version_id")
        val versionId: Int,
        @SerializedName("scenario_id")
        val scenarioId: Int,
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
        val status: Boolean?
): Parcelable