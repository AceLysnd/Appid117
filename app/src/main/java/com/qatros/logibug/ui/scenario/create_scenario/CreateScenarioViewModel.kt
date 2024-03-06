package com.qatros.logibug.ui.scenario.create_scenario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.request.ai.AICommandRequest
import com.qatros.logibug.core.data.response.ai.AICommandResponse
import com.qatros.logibug.core.data.response.member.ListAllMemberResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateScenarioViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _aiResponse = MutableLiveData<AICommandResponse>()
    val aiResponse: LiveData<AICommandResponse> = _aiResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun generateAi(aiCommand: AICommandRequest){
        _loading.value = true
        viewModelScope.launch {
            repository.generateAi(aiCommand).let {
                if (it.isSuccessful){
                    _message.value = "Successfully generating results"
                    _aiResponse.value = it.body()
                    _loading.value = false
                }else{
                    _message.value = "Failed to generate results"
                }
            }
        }
    }

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