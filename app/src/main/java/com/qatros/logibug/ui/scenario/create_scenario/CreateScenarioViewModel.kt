package com.qatros.logibug.ui.scenario.create_scenario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateScenarioViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message = _message

    fun createScenario(token: String, scenarioName: String, projectId: Int){
        viewModelScope.launch {
            repository.createScenario(token, projectId, scenarioName).let {
                if (it.isSuccessful){
                    _message.value = "Successfully added scenario"
                }else{
                    _message.value = "Failed to add scenario"
                }
            }
        }
    }

}