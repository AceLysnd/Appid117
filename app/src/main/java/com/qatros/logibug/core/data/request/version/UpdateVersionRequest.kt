package com.qatros.logibug.core.data.request.version

import com.google.gson.annotations.SerializedName

data class UpdateVersionRequest(
    @field:SerializedName("name")
    val name: String
)