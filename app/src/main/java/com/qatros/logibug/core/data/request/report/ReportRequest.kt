package com.qatros.logibug.core.data.request.report

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportRequest(
    @SerializedName("id")
    val idProject: Int
) : Parcelable