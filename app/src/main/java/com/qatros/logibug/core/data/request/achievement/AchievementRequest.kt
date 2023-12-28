package com.qatros.logibug.core.data.request.achievement

import com.google.gson.annotations.SerializedName

class AchievementRequest(
    @SerializedName("user_id")
    val userId: Int
)