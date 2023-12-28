package com.qatros.logibug.ui.account.account_setting

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
class AccountSettingViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    fun addProfile(token: String, name: String, imgFile: String) {
        viewModelScope.launch {
            val requestImageFile = File(imgFile).asRequestBody("image/jpg".toMediaTypeOrNull())
            Log.d("test", "addProfile: $requestImageFile")
            val dataMulti = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("img_url", "data.png", requestImageFile)
                .build()
            repository.updateProfile(token, dataMulti).let {
                if (it.isSuccessful) {
                    _message.value = "isSuccessful"
                } else {
                    _message.value = "filed to edit"
                }
            }
        }
    }
}