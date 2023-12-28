package com.qatros.logibug.ui.version.create_version

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateVersionViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    fun createVersion(token: String, name: String, projectId: Int){
        viewModelScope.launch {
            repository.createVersion(token, name, projectId).let {
                if (it.isSuccessful){
                    _message.value = "Berhasil menambahkan version"
                }else{
                    _message.value = "Gagal menambahkan version"
                }
            }
        }
    }
}