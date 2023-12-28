package com.qatros.logibug.core.data.request.result

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTestCaseResultRequest(
    @SerializedName("test_case_id")
    val tescaseid : Int
): Parcelable