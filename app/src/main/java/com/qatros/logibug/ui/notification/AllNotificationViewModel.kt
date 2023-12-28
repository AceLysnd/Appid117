package com.qatros.logibug.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.notification.NotificationAllResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class AllNotificationViewModel @Inject constructor (private val repository: RemoteRepository):
ViewModel(){

    private val _getNotification = MutableLiveData<NotificationAllResponse>()
    val notification: LiveData<NotificationAllResponse> = _getNotification

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _totalUnread = MutableLiveData<Int>()
    val totalUnread = _totalUnread

    fun getAllNotifications(token: String){
        viewModelScope.launch {
            repository.getAllNotifications(token).let {
                if (it.isSuccessful){
                    _getNotification.value = it.body()
                }
            }
        }
    }
}