package com.qatros.logibug.ui.testcase.filter_test_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.scenario.ListScenarioResponse
import com.qatros.logibug.core.data.response.test_case.FilterTestCaseResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterTestCaseViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _listFilterTestCase = MutableLiveData<FilterTestCaseResponse>()
    val listFilterTestCase: LiveData<FilterTestCaseResponse> = _listFilterTestCase

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _scenario = MutableLiveData<ListScenarioResponse>()
    val scenario: LiveData<ListScenarioResponse> = _scenario

    private val _message = MutableLiveData<String>()
    val message = _message

    fun getScenarioDropdown(token: String, projectId: Int) {
        viewModelScope.launch {
            repository.getAllScenario(token, projectId).let {
                if (it.isSuccessful) {
                    _scenario.value = it.body()
                }
            }
        }
    }

    fun getListFilterTestCase(token: String, versionId: Int, scenarioId: Int) {
        _loading.value = true
        viewModelScope.launch {
            repository.filterTestCase(token, versionId, scenarioId).let {
                if (it.isSuccessful){
                    _listFilterTestCase.value = it.body()
                    _loading.value = false

                }
            }
        }
    }

}