package br.com.fiap.email.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.email.models.ReceivedEmail
import br.com.fiap.email.network.ApiClient
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {
    val userName = MutableLiveData<String>()

    fun setUserName(name: String) {
        userName.value = name
    }

    private val _receivedEmails = MutableLiveData<List<ReceivedEmail>>()
    val receivedEmails: LiveData<List<ReceivedEmail>> get() = _receivedEmails

    fun fetchReceivedEmails(userId: String) {
        viewModelScope.launch {
            try {
                val emails = ApiClient.authService.getReceivedEmails(userId)
                _receivedEmails.value = emails
            } catch (e: Exception) {

            }
        }
    }
}