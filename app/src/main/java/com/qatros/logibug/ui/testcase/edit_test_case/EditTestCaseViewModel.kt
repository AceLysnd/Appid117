package com.qatros.logibug.ui.testcase.edit_test_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.scenario.ListScenarioResponse
import com.qatros.logibug.core.data.response.test_case.TestCaseByIdResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTestCaseViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _testCase = MutableLiveData<TestCaseByIdResponse>()
    val testCase: LiveData<TestCaseByIdResponse> = _testCase

    private val _scenario = MutableLiveData<ListScenarioResponse>()
    val scenario: LiveData<ListScenarioResponse> = _scenario

    private val _message = MutableLiveData<String>()
    val message = _message

    fun getTestCaseById(token: String, testCaseId: Int){
        viewModelScope.launch {
            repository.getTestCaseById(token, testCaseId).let {
                if (it.isSuccessful){
                    _testCase.value = it.body()
                }
            }
        }
    }

    fun updateTestCase(
        token: String,
        testCaseId: Int,
        testCaseCategory: String,
        preCondition: String,
        testCaseName: String,
        testStep: String,
        expectation: String
    ){
        viewModelScope.launch {
            repository.updateTestCase(
                token,
                testCaseId,
                testCaseCategory,
                preCondition,
                testCaseName,
                testStep,
                expectation
            ).let {
                if (it.isSuccessful){
                    _message.value = "Berhasil mengubah test case"
                }else{
                    _message.value = "Gagal mengubah test case"
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