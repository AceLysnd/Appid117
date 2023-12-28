package com.qatros.logibug.ui.report.list_report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.project.ListProjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListAllReportViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _listProject = MutableLiveData<ListProjectResponse>()
    val listProject: LiveData<ListProjectResponse> = _listProject

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getReportAllProject(token: String, typeTest: String) {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                val response = repository.getReportAllProject(token)
                if (response.isSuccessful) {
                    _listProject.postValue(response.body())
                } else {
                    _message.postValue(response.message())
                }
            }
            _loading.value = false
        }
    }
}