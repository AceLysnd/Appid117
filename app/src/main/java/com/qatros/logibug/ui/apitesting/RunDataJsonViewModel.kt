package com.qatros.logibug.ui.apitesting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.api_testing.RunDataJsonResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunDataJsonViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _runDataJson = MutableLiveData<RunDataJsonResponse>()
    val runDataJson: LiveData<RunDataJsonResponse> = _runDataJson

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun runDataJson(token: String, versionId: Int, dataId: Int) {
        viewModelScope.launch {
            repository.runDataJson(
                token, versionId, dataId
            ).let {
                if (it.isSuccessful){
                    _runDataJson.value = it.body()
                }
            }
        }
    }

}