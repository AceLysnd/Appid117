package com.qatros.logibug.ui.report.report_automation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.report.ReportAutomationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListReportAutomationViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _reportData = MutableLiveData<ReportAutomationResponse>()
    val reportData: LiveData<ReportAutomationResponse> = _reportData

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadReportData(token: String, projectId : Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                delay(1000)

                val response = repository.getAllReportAutomation(token, projectId)
                if (response.isSuccessful) {
                    _reportData.value = response.body()
                } else {
                    _message.value = "success"
                }
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}