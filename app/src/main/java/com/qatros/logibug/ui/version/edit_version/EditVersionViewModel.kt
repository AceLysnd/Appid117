package com.qatros.logibug.ui.version.edit_version

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.version.GetVersionByIdResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditVersionViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _version = MutableLiveData<GetVersionByIdResponse>()
    val version: LiveData<GetVersionByIdResponse> = _version

    private val _message = MutableLiveData<String>()
    val message = _message

    fun getVersionById(token: String, versionId: Int){
        viewModelScope.launch {
            repository.getVersionById(token, versionId).let {
                if (it.isSuccessful){
                    _version.value = it.body()
                }
            }
        }
    }

    fun updateVersion(token: String, versionId: Int, name: String){
        viewModelScope.launch {
            repository.updateVersion(token, versionId, name).let {
                if (it.isSuccessful){
                    _message.value = "Berhasil mengubah versi"
                }else{
                    _message.value = "Gagal mengubah versi"
                }
            }
        }
    }

}