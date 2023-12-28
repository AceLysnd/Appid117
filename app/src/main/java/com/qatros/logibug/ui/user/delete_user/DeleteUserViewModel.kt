package com.qatros.logibug.ui.user.delete_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.delete_account.DeleteUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteUserViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _deleteUserData = MutableLiveData<DeleteUserResponse>()
    val deleteUserData: LiveData<DeleteUserResponse> = _deleteUserData

    private val _deleteUser = MutableLiveData<DeleteUserResponse>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun deleteUser(token: String, idUser: Int) {
        viewModelScope.launch {
            repository.deleteUser(token, idUser).let {
                if (it.isSuccessful) {
                    _deleteUser.value = it.body()
                    _message.value = "User success deleted"
                } else {
                    _message.value = "Gagal Menghapus Account"
                }
            }
        }
    }
}