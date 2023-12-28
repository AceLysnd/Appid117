package com.qatros.logibug.ui.scenario.list_scenario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.scenario.DeleteScenarioResponse
import com.qatros.logibug.core.data.response.scenario.ListScenarioResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScenarioViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {
    private val _listScenario = MutableLiveData<ListScenarioResponse>()
    val listScenario: LiveData<ListScenarioResponse> = _listScenario

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _deleteScenario = MutableLiveData<DeleteScenarioResponse>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getAllScenario(token: String, projectId: Int){
        _loading.value = true
        viewModelScope.launch {
            repository.getAllScenario(token, projectId).let {
                if (it.isSuccessful){
                    _listScenario.value = it.body()
                    _loading.value = false
                }
            }
        }
    }

    fun deleteScenario(token: String, scenarioId: Int){
        viewModelScope.launch {
            repository.deleteScenario(token, scenarioId).let {
                if (it.isSuccessful){
                    _deleteScenario.value = it.body()
                    _message.value = "Berhasil mengahapus scenario"
                }else{
                    _message.value = "Gagal menghapus scenario"
                }
            }
        }
    }

}