package com.qatros.logibug.ui.testcase.list_test_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.test_case.DeleteTestCaseResponse
import com.qatros.logibug.core.data.response.test_case.GetAllTestCaseResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTestCaseViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {
    private val _listTestCase = MutableLiveData<GetAllTestCaseResponse>()
    val listTestCase: LiveData<GetAllTestCaseResponse> = _listTestCase

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _deleteTestCase = MutableLiveData<DeleteTestCaseResponse>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    fun getAllTestCase(token: String, versionId: Int){
        _loading.value = true
        viewModelScope.launch {
            repository.getAllTestCase(token, versionId).let {
                if (it.isSuccessful){
                    _listTestCase.value = it.body()
                    _loading.value = false
                }
            }
        }
    }

    fun deleteTestCase(token: String, testCaseId: Int){
        viewModelScope.launch {
            repository.deleteTestCase(token, testCaseId).let {
                if (it.isSuccessful){
                    _deleteTestCase.value = it.body()
                    _message.value = "Berhasil menghapus test case"
                }else{
                    _message.value = "Gagal menghapus test case"
                }
            }
        }
    }

}