package com.qatros.logibug.core.data.response.result

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeletedResultsResponse(
    @field:SerializedName("success deleted")
    val message: String,
    @field:SerializedName("data")
    val data: DeletdResultData
): Parcelable

@Parcelize
data class DeletdResultData(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("test_case_id")
    val testCaseId: Int,
    @field:SerializedName("actual")
    val actual: String,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("priority")
    val priority: String,
    @field:SerializedName("severity")
    val severity: String,
    @field:SerializedName("img_url")
    val imgUrl: String,
    @field:SerializedName("note")
    val note: String
): Parcelable