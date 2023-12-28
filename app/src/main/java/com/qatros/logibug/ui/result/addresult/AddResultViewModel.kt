package com.qatros.logibug.ui.result.addresult

import androidx.lifecycle.LiveData
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
class AddResultViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun addResultProject(token: String, tesCaseId: Int, actual: String, status: String, priority: String, severity: String, imgUrl: File, note: String){
        viewModelScope.launch {
            val requestImageFile = imgUrl.asRequestBody("image/jpg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("actual", actual)
                .addFormDataPart("status", status)
                .addFormDataPart("priority", priority)
                .addFormDataPart("severity", severity)
                .addFormDataPart("note", note)
                .addFormDataPart("test_case_id", tesCaseId.toString())
                .addFormDataPart("img_url", imgUrl.name, requestImageFile)
                .build()

            repository.addResult(token, multipartBody).let {
                if (it.isSuccessful) {
                    _loading.postValue(true)
                    _message.value = "Berhasil menyimpan result"
                } else {
                    _message.value = "Gagal menyimpan"
                }
            }
        }
    }
}