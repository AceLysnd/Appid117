package com.qatros.logibug.core.data.request.profile

import com.google.gson.annotations.SerializedName

data class ProfileRequest(
    @SerializedName("com")
    val name: Int,
)