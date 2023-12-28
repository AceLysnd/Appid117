package com.qatros.logibug.ui.result.detailresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.result.DeletedResultsResponse
import com.qatros.logibug.core.data.response.result.DetailTestCaseResultResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTestCaseViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {
    private val _detailTestCase = MutableLiveData<DetailTestCaseResultResponse>()
    val detailTestCaseResult: LiveData<DetailTestCaseResultResponse> = _detailTestCase

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _deleteResult = MutableLiveData<DeletedResultsResponse>()

    fun getDetailTestCase(token: String, TestCaseId: Int){
        viewModelScope.launch {
            repository.getResult(token, TestCaseId).let {
                if (it.isSuccessful)
                    _detailTestCase.value = it.body()
            }
        }
    }

    fun deletedResult(token: String, user_Id: Int){
        viewModelScope.launch {
            repository.deletedResults(token, user_Id).let {
                if (it.isSuccessful){
                    _deleteResult.value = it.body()
                    _message.value = "Berhasil menghapus result"
                } else {
                    _message.value = "Gagal menghapus result"
                }
            }
        }
    }
}