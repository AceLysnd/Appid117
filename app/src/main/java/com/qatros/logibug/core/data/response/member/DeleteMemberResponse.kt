package id.qatros.logibug.core.data.source.remote.response.member

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteMemberResponse (
    @SerializedName("message")
    val message: String
        ): Parcelable