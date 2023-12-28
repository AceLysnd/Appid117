package com.qatros.logibug.ui.testcase.create_test_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.scenario.ListScenarioResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTestCaseViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    private val _scenario = MutableLiveData<ListScenarioResponse>()
    val scenario: LiveData<ListScenarioResponse> = _scenario

    fun createTestCase(
        token: String,
        testCaseCategory: String,
        scenarioId: Int,
        versionId: Int,
        preCondition: String,
        testCase: String,
        testStep: String,
        expectation: String
    ) {
        viewModelScope.launch {
            repository.createTestCase(
                token,
                testCaseCategory,
                scenarioId,
                versionId,
                preCondition,
                testCase,
                testStep,
                expectation
            ).let {
                if (it.isSuccessful) {
                    _message.value = "Berhasil Menambahkan Test Case"
                } else {
                    _message.value = "Gagal Menambahkan Test Case"
                }
            }
        }
    }

    fun getScenarioDropdown(token: String, projectId: Int){
        viewModelScope.launch {
            repository.getAllScenario(token, projectId).let {
                if (it.isSuccessful){
                    _scenario.value = it.body()
                }
            }
        }
    }

}