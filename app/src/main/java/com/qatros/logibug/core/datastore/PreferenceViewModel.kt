package com.qatros.logibug.core.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel@Inject constructor(private val preference: SharedPreference): ViewModel() {

    fun saveUser(
        token: String,
        refreshToken: String,
        exp: Int,
        email: String,
        id: Int,
        name: String,
    ){
        viewModelScope.launch {
            preference.saveState(
                token,
                refreshToken,
                exp,
                email,
                id,
                name,
                imgUrl = null
            )
        }
    }

    fun saveOnboardingState(){
        viewModelScope.launch {
            preference.saveOnboardingState()
        }
    }

    fun saveLoginState(){
        viewModelScope.launch {
            preference.saveLoginState()
        }
    }


    fun getLoginState(): LiveData<SessionModel> {
        return preference.getState().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            preference.logout()
        }
    }
}