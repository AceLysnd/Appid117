package com.qatros.logibug.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.request.achievement.AchievementRequest
import com.qatros.logibug.core.data.response.achievement.AchievementResponse
import com.qatros.logibug.core.data.response.profile.Profiles
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetProfilesViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {
    private val _profiles = MutableLiveData<Profiles>()
    val profiles: MutableLiveData<Profiles> = _profiles

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: MutableLiveData<Boolean> = _isLogin

    private val _achievementData = MutableLiveData<AchievementResponse>()
    val achievementData: LiveData<AchievementResponse> = _achievementData

    private val _achievementReq = MutableLiveData<AchievementRequest>()
    val achievementReq: LiveData<AchievementRequest> = _achievementReq

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun getAchievement(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                delay(1000)

                val response = repository.getAchievement(token, userId)
                if (response.isSuccessful) {
                    _achievementData.value = response.body()
                }
            }finally {
                _isLoading.value = false
            }
        }
    }
}