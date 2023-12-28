package com.qatros.logibug.ui.homepage.list_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.project.DeleteProjectResponse
import com.qatros.logibug.core.data.response.project.ListProjectResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProjectViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _listProject = MutableLiveData<ListProjectResponse>()
    val listProject: LiveData<ListProjectResponse> = _listProject

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _deleteProject = MutableLiveData<DeleteProjectResponse>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getAllProject(token: String) {
        _loading.value = true
        viewModelScope.launch {
            repository.getAllProject(token).let {
                if (it.isSuccessful) {
                    _listProject.value = it.body()
                    _loading.value = false
                }
            }
        }
    }

    fun deleteProject(token: String, id: Int) {
        viewModelScope.launch {
            repository.deleteProject(token, id).let {
                if (it.isSuccessful) {
                    _deleteProject.value = it.body()
                    _message.value = "Berhasil Menghapus Project"
                } else {
                    _message.value = "Gagal Menghapus Project"
                }
            }
        }
    }

}