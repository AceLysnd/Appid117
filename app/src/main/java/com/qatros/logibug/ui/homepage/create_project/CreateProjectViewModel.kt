package com.qatros.logibug.ui.homepage.create_project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    fun createProject(token: String, projectName: String, typeTest: String, platform: String) {
        viewModelScope.launch {
            repository.createProject(token, projectName, typeTest, platform).let {
                if (it.isSuccessful) {
                    _message.value = "Berhasil Menambahkan Project"
                } else {
                    _message.value = "Gagal Menambahkan Project"
                }
            }
        }
    }
}