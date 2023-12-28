package com.qatros.logibug.ui.member.invite_member

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteMemberViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {
    private val _message = MutableLiveData<String>()
    val message = _message

    fun inviteMember(token: String, projectId: Int, email: String, role: String){
        viewModelScope.launch {
            repository.inviteMember(token, projectId, email, role).let {
                if (it.isSuccessful){
                    _message.value = "Success for invite"
                }else{
                    _message.value = "Failed to invite"
                }
            }
        }
    }

}