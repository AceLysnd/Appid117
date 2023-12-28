package com.qatros.logibug.ui.role

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qatros.logibug.core.data.RemoteRepository
import com.qatros.logibug.core.data.response.role.RoleResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _role = MutableLiveData<RoleResponse>()
    val role: LiveData<RoleResponse> = _role

    fun getRole(token: String, projectId: Int){
        viewModelScope.launch {
            repository.getRole(
                token, projectId
            ).let {
                if (it.isSuccessful){
                    _role.value = it.body()
                }
            }
        }
    }

}