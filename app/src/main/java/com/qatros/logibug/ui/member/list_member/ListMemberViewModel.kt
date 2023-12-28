package com.qatros.logibug.ui.member.list_member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.member.ListAllMemberResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMemberViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel(){

    private val _listMember = MutableLiveData<ListAllMemberResponse>()
    val listMember: LiveData<ListAllMemberResponse> = _listMember

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _messages = MutableLiveData<String>()
    val messages: LiveData<String> = _messages

    fun getAllMember(token: String, projectId: Int){
        _loading.value = true
        viewModelScope.launch {
            repository.getAllMember(token, projectId).let {
                if (it.isSuccessful){
                    _listMember.value = it.body()
                    _loading.value = false
                }
            }
        }
    }

    fun deleteMember(token: String, memberId: Int){
        viewModelScope.launch {
            repository.deleteMember(token, memberId).let {
                if (it.isSuccessful){
                    _messages.value = "Berhasil menghapus member"
                }else{
                    _messages.value = "Gagal menghapus member"
                }
            }
        }
    }

}