package com.qatros.logibug.ui.apitesting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadFileJsonViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _message = MutableLiveData<String>()
    val message = _message

    fun uploadFileJson(token: String, versionId: Int, fileJson: String){
        viewModelScope.launch {
            val requestUploadFile = File(fileJson).asRequestBody("application/json".toMediaTypeOrNull())
            Log.d("test", "uploadFileJson: $requestUploadFile")
            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("version_id", versionId.toString())
                .addFormDataPart("json_url", "data.json", requestUploadFile)
                .build()

            repository.uploadFileJson(token, multipartBody).let {
                if (it.isSuccessful) {
                    _message.value = "Berhasil menyimpan file"
                } else {
                    _message.value = "Gagal menyimpan file"
                }
            }

        }
    }

}