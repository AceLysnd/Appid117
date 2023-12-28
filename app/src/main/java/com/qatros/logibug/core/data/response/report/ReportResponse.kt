package com.qatros.logibug.core.data.response.report

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReportResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("versions")
    val versions: List<Version>
) : Parcelable

@Parcelize
data class Version(
    @SerializedName("percentage")
    val percentage: Float,
    @SerializedName("test_case_count")
    val testCaseCount: Int,
    @SerializedName("test_case_fail_count")
    val testCaseFailCount: Int,
    @SerializedName("test_case_pass_count")
    val testCasePassCount: Int,
    @SerializedName("version_name")
    val versionName: String
) : Parcelable