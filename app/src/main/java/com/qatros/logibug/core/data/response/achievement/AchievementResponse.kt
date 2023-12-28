package com.qatros.logibug.core.data.response.achievement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AchievementResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user")
    val user: String,
    @SerializedName("testcase_count")
    val testcaseCount: Int,
    @SerializedName("rank")
    val rank: RankData
) : Parcelable

@Parcelize
data class RankData(
    @SerializedName("name")
    val nameRank: String,
    @SerializedName("range_difference")
    val rankDifference: Int
) : Parcelable