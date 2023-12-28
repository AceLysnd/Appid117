package com.qatros.logibug.ui.version.clone_version

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CloneVersionViewModel @Inject constructor(val repository: RemoteRepository): ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    fun cloneVersion(token: String, versionId: Int){
        viewModelScope.launch {
            repository.cloneVersion(token, versionId).let {
                if (it.isSuccessful){
                    _message.value = "Berhasil menduplicate version"
                }else{
                    _message.value = "Gagal menduplicate version"
                }
            }
        }
    }
}