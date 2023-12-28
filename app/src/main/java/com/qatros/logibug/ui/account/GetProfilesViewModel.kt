package com.qatros.logibug.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.profile.Profiles
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetProfilesViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {
    private val _profiles = MutableLiveData<Profiles>()
    val profiles: MutableLiveData<Profiles> = _profiles

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: MutableLiveData<Boolean> = _isLogin

    fun getProfiles(token: String) {
        viewModelScope.launch {
            repository.getProfiles(token).let {
                if (it.code() == 401) {
                    _isLogin.value = false
                } else {
                    if (it.isSuccessful) {
                        _profiles.value = it.body()
                    }
                    _isLogin.value = true
                }
            }
        }
    }
}