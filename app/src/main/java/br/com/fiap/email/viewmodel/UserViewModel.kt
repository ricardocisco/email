package br.com.fiap.email.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.email.models.ArchivedEmail
import br.com.fiap.email.models.Email
import br.com.fiap.email.models.Emails
import br.com.fiap.email.models.ReceivedEmail
import br.com.fiap.email.models.TrashEmail
import br.com.fiap.email.network.ApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.UUID

class UserViewModel() : ViewModel() {

    val userName = MutableLiveData<String>()
    val userId = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()

    fun setUserName(name: String) {
        userName.value = name
    }

    fun setUserId(id: String) {
        userId.value = id
    }

    fun setUserEmail(email: String) {
        userEmail.value = email
    }

    private val _message = mutableStateOf<MessageState?>(null)
    val message: State<MessageState?> get() = _message

    fun sendEmail(userId: String, sentEmail: String, subject: String, body: String){
        val email = Email(
            emailId = "",
            sentEmail = sentEmail,
            sentNome = "",
            subject = subject,
            body = body,
            sentAt = LocalDateTime.now().toString(),
            isSpam = false
        )

        ApiClient.authService.sentEmail(userId, email).enqueue(object : Callback<Email> {
            override fun onResponse(call: Call<Email>, response: Response<Email>) {
                if (response.isSuccessful) {
                    _message.value = MessageState.Success
                }
                else {
                    _message.value = MessageState.Error("Falha ao enviar o email")
                }
            }

            override fun onFailure(call: Call<Email>, t: Throwable) {
                _message.value = MessageState.Error("Falha ao enviar o email: ${t.message}")
            }
        })
    }

    private val _receivedEmails = MutableLiveData<List<ReceivedEmail>>()
    val receivedEmails: LiveData<List<ReceivedEmail>> get() = _receivedEmails

    private val _sentEmails = MutableLiveData<List<Email>>()
    val sentEmails: LiveData<List<Email>> get() = _sentEmails

    private val _archivedEmails = MutableLiveData<List<ArchivedEmail>>()
    val archivesEmails: LiveData<List<ArchivedEmail>> get() = _archivedEmails

    private val _trashEmails = MutableLiveData<List<TrashEmail>>()
    val trashEmails: LiveData<List<TrashEmail>> get() = _trashEmails

    fun fetchReceivedEmails(userId: String) {
        ApiClient.authService.getUserEmails(userId).enqueue(object : Callback<Emails> {
            override fun onResponse(call: Call<Emails>, response: Response<Emails>) {
                if (response.isSuccessful) {
                    _receivedEmails.value = response.body()?.received
                    _message.value = MessageState.Success
                } else {
                    _message.value = MessageState.Error("Falha ao carregar os emails")
                }
            }

            override fun onFailure(call: Call<Emails>, t: Throwable) {
                _message.value = MessageState.Error("Falha ao carregar os emails: ${t.message}")
            }
        })
    }

    fun fetchSentEmails(userId: String) {
        ApiClient.authService.getUserEmails(userId).enqueue(object : Callback<Emails> {
            override fun onResponse(call: Call<Emails>, response: Response<Emails>) {
                if (response.isSuccessful) {
                    _sentEmails.value = response.body()?.sent
                    _message.value = MessageState.Success
                } else {
                    _message.value = MessageState.Error("Falha ao carregar os emails")
                }
            }

            override fun onFailure(call: Call<Emails>, t: Throwable) {
                _message.value = MessageState.Error("Falha ao carregar os emails: ${t.message}")
            }
        })
    }

    fun fetchArchivedEmails(userId: String) {
        ApiClient.authService.getUserEmails(userId).enqueue(object : Callback<Emails> {
            override fun onResponse(call: Call<Emails>, response: Response<Emails>) {
                if(response.isSuccessful){
                    _archivedEmails.value = response.body()?.archived
                    _message.value = MessageState.Success
                } else {
                    _message.value = MessageState.Error("Falha ao carregar os emails")
                }
            }

            override fun onFailure(call: Call<Emails>, t: Throwable) {
                _message.value = MessageState.Error("Falha ao carregar os emails: ${t.message}")
            }
        })
    }

    fun fetchTrashEmails(userId: String){
        ApiClient.authService.getUserEmails(userId).enqueue(object : Callback<Emails>{
            override fun onResponse(call: Call<Emails>, response: Response<Emails>) {
                if (response.isSuccessful){
                    _trashEmails.value = response.body()?.trash
                    _message.value = MessageState.Success
                }else {
                    _message.value = MessageState.Error("Falha ao carregar os emails")
                }
            }

            override fun onFailure(call: Call<Emails>, t: Throwable) {
                _message.value = MessageState.Error("Falha ao carregar os emails: ${t.message}")
            }
        })
    }


}

sealed class MessageState {
    object Success : MessageState()
    data class Error(val message: String) : MessageState()
}