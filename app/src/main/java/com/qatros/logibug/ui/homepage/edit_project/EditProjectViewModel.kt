package com.qatros.logibug.ui.homepage.edit_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.project.ProjectByIdDetail
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProjectViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _project = MutableLiveData<ProjectByIdDetail>()
    val project: LiveData<ProjectByIdDetail> = _project

    private val _message = MutableLiveData<String>()
    val message = _message

    fun geProjectById(token: String, id: Int) {
        viewModelScope.launch {
            repository.getProjectById(token, id).let {
                if (it.isSuccessful) {
                    _project.value = it.body()?.data
                }
            }
        }
    }

    fun updateProject(token: String, id: Int, name: String, typeTest: String, platform: String) {
        viewModelScope.launch {
            repository.updateProject(token, id, name, typeTest, platform).let {
                if (it.isSuccessful) {
                    _message.value = "Berhasil Mengubah Project"
                } else {
                    _message.value = "Gagal Mengubah Project"
                }
            }
        }
    }

}