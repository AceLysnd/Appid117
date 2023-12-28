package com.qatros.logibug.ui.account.achievement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.request.achievement.AchievementRequest
import com.qatros.logibug.core.data.response.achievement.AchievementResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _achievementData = MutableLiveData<AchievementResponse>()
    val achievementData: LiveData<AchievementResponse> = _achievementData

    private val _achievementReq = MutableLiveData<AchievementRequest>()
    val achievementReq: LiveData<AchievementRequest> = _achievementReq

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAchievementByUserId(token: String, userId: Int){
        viewModelScope.launch {
            repository.getAchievement(token, userId).let {
                if (it.isSuccessful)
                    _achievementData.value = it.body()
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
