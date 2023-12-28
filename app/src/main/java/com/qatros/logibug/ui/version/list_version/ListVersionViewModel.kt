package com.qatros.logibug.ui.version.list_version

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.version.DeleteVersionResponse
import com.qatros.logibug.core.data.response.version.ListAllVersionResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListVersionViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel(){

    private val _listVersion = MutableLiveData<ListAllVersionResponse>()
    val listVersion: LiveData<ListAllVersionResponse> = _listVersion

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _deleteVersion = MutableLiveData<DeleteVersionResponse>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getAllVersion(token: String, projectId: Int){
        _loading.value = true
        viewModelScope.launch {
            repository.getAllVersion(token, projectId).let {
                if (it.isSuccessful){
                    _listVersion.value = it.body()
                    _loading.value = false
                }
            }
        }
    }

    fun deleteVersion(token: String, versionId: Int){
        viewModelScope.launch {
            repository.deleteVersion(token, versionId).let {
                if (it.isSuccessful){
                    _deleteVersion.value = it.body()
                    _message.value = "Berhasil mengahapus version"
                }else{
                    _message.value = "Gagal menghapus version"
                }
            }
        }
    }

}