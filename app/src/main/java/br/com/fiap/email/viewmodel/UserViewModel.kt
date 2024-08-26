package br.com.fiap.email.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.email.models.ReceivedEmail
import br.com.fiap.email.network.ApiClient
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {
    val userName = MutableLiveData<String>()
    val userId = MutableLiveData<String>()
    val receivedEmails = MutableLiveData<List<ReceivedEmail>>()
    val userEmail = MutableLiveData<String>()

    fun setUserName(name: String) {
        userName.value = name
    }

    fun setUserId(id: String) {
        userId.value = id
    }

    fun setReceivedEmails(emails: List<ReceivedEmail>){
        receivedEmails.value = emails
    }

    fun setUserEmail(email: String) {
        userEmail.value = email
    }


}