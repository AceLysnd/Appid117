package com.qatros.logibug.core.data.response.notification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationAllResponse (
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("total_unread")
    val total_unread: Int,
    @field:SerializedName("current_page")
    val current_page : String,
    @field:SerializedName("total_pages")
    val total_pages: String,
    @field:SerializedName("data")
    val data : List<AddAllNotificationData>
): Parcelable

@Parcelize
data class AddAllNotificationData (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("recipient_type")
    val recipient_type: String,
    @field:SerializedName("recipient_id")
    val recipient_id : Int,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("params")
    val params : ParamsData,
    @field:SerializedName("read_at")
    val read_at: String,
    @field:SerializedName("created_at")
    val created_at: String,
    @field:SerializedName("updated_at")
    val updated_at : String
    ): Parcelable

@Parcelize
data class ParamsData (
    @field:SerializedName("result")
    val result: ResultData
): Parcelable

@Parcelize
data class ResultData (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("project_name")
    val project_name: String
): Parcelable