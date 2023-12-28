package com.qatros.logibug.core.data.request.result

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class EditResultRequest (
        @SerializedName("test_case_id")
        val tescaseid : Int,
        @SerializedName("actual")
        val actual : String,
        @SerializedName("status")
        val status : String,
        @SerializedName("priority")
        val priority : String,
        @SerializedName("severity")
        val severity : String,
        @SerializedName("img_url")
        val imgUrl : MultipartBody.Part,
        @SerializedName("note")
        val note : String
        )