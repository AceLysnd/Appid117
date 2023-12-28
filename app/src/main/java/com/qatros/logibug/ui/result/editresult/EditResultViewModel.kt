package com.qatros.logibug.ui.result.editresult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.result.GetResultsByIdResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditResultViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _results = MutableLiveData<GetResultsByIdResponse>()
    val results: LiveData<GetResultsByIdResponse> = _results

    private val _message = MutableLiveData<String>()
    val message = _message

    fun getResultsById(token: String, testCaseId: Int){
        viewModelScope.launch {
            repository.getResultsById(token, testCaseId).let {
                if (it.isSuccessful) {
                    _results.value = it.body()
                }
            }
        }
    }

    fun updateResults(
        token: String,
        test_case_id: Int,
        resultId: Int,
        actual: String,
        status: String,
        priority: String,
        severity: String,
        imageFile: File,
        noteResult: String
    ){
        viewModelScope.launch {
            val requestImageFile = imageFile.asRequestBody("image/jpg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("actual", actual)
                .addFormDataPart("status", status)
                .addFormDataPart("priority", priority)
                .addFormDataPart("severity", severity)
                .addFormDataPart("note", noteResult)
                .addFormDataPart("test_case_id", test_case_id.toString())
                .addFormDataPart("img_url", imageFile.name, requestImageFile)
                .build()

            repository.editResult(token, resultId ,multipartBody).let {
                if (it.isSuccessful) {
                    _message.value = "Berhasil mengubah result"
                } else {
                    Log.d("test1", "updateTestCase: $test_case_id")
                    _message.value = "Gagal menyimpan"
                }
            }
        }
    }
}