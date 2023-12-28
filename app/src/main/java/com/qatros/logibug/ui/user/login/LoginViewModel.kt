package com.qatros.logibug.ui.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.login.LoginResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(private val repository: RemoteRepository): ViewModel(){

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse = _loginResponse

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    fun login(email: String, password: String){
        viewModelScope.launch {
            repository.login(email, password).let {
                if (it.isSuccessful){
                    _isSuccess.value = true
                    _loginResponse.value = it.body()
                }else{
                    _isSuccess.value = false
                }
            }
        }
    }
}