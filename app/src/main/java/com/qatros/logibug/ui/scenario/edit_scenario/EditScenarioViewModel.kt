package com.qatros.logibug.ui.scenario.edit_scenario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.scenario.ScenarioByIdResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditScenarioViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel(){

    private val _scenario = MutableLiveData<ScenarioByIdResponse>()
    val scenario: LiveData<ScenarioByIdResponse> = _scenario

    private val _message = MutableLiveData<String>()
    val message = _message

    fun getScenarioById(token: String, scenarioId: Int){
        viewModelScope.launch {
            repository.getScenarioById(token, scenarioId).let {
                if (it.isSuccessful){
                    _scenario.value = it.body()
                }
            }
        }
    }

    fun updateScenario(token: String, scenarioId: Int, name: String){
        viewModelScope.launch {
            repository.updateScenario(token, scenarioId, name).let {
                if (it.isSuccessful){
                    _message.value = "Berhasil mengubah scenario"
                }else{
                    _message.value = "Gagal mengubah scenario"
                }
            }
        }
    }

}