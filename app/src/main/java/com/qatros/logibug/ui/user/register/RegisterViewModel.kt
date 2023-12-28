package com.qatros.logibug.ui.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.register.RegisterResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse = _registerResponse

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    fun register(name: String, email: String, password: String, confirmationPassword: String) {
        viewModelScope.launch {
            repository.register(name, email, password, confirmationPassword).let {
                if (it.isSuccessful) {
                    _isSuccess.value = true
                    _registerResponse.value = it.body()
                } else {
                    _isSuccess.value = false
                }
            }
        }
    }

}