package com.qatros.logibug.core.data.response.report

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReportAutomationResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("data")
    val dataAutomation: DataAutomation,

) : Parcelable

@Parcelize
data class DataAutomation(
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("version")
    val versionAutomation: List<VersionAutomation>
) : Parcelable

@Parcelize
data class VersionAutomation(
    @SerializedName("version_name")
    val versionName: String,
    @SerializedName("req_total")
    val reqTotal: Int,
    @SerializedName("report_request")
    val reportRequest: List<ReportRequest>
) : Parcelable

@Parcelize
data class ReportRequest(
    @SerializedName("res_code")
    val resCode: String,
    @SerializedName("res_count")
    val resCount: Int
) : Parcelable
