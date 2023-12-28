package com.qatros.logibug.core.data.response.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateProjectResponse(
    @field:SerializedName("data")
    val data: ProjectData,
    @field:SerializedName("message")
    val message: String
) : Parcelable

@Parcelize
data class ProjectData(
    @field: SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("type_test")
    val typeTest: String,
    @field:SerializedName("platform")
    val platform: String,
    @field:SerializedName("version")
    val version: Version
) : Parcelable

@Parcelize
data class Version(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("status")
    val status: Boolean,
    @field:SerializedName("project_id")
    val projectId: Int
) : Parcelable