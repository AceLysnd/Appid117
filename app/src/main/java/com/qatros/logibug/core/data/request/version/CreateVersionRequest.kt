package com.qatros.logibug.core.data.request.version

import com.google.gson.annotations.SerializedName


data class CreateVersionRequest(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("project_id")
    val projectId: Int
)
