package br.com.fiap.email.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.fiap.email.models.MoveArchivedRequest
import br.com.fiap.email.models.MoveEmailsRequest
import br.com.fiap.email.models.ReceivedEmail
import br.com.fiap.email.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListEmailViewModel : ViewModel() {

    private val _favoriteEmails = mutableStateOf<Set<Int>>(setOf())

    val favoriteEmails: Set<Int>
        get() = _favoriteEmails.value

    fun toggleFavorite(emailIndex: Int) {
        val updatedFavorites = _favoriteEmails.value.toMutableSet()

        if (updatedFavorites.contains(emailIndex)) {
            updatedFavorites.remove(emailIndex)
        } else {
            updatedFavorites.add(emailIndex)
        }

        _favoriteEmails.value = updatedFavorites
    }

    fun isFavorite(emailIndex: Int): Boolean {
        return favoriteEmails.contains(emailIndex)
    }

    private val _selectedItems = mutableStateListOf<Int>()
    val selectedItems: List<Int> = _selectedItems

    private val _isInEditMode = mutableStateOf(false)
    val isInEditMode: State<Boolean> = _isInEditMode

    fun toggleItemSelected(index: Int) {
        if (_selectedItems.contains(index)) {
            _selectedItems.remove(index)
        } else {
            _selectedItems.add(index)
        }
        _isInEditMode.value = _selectedItems.isNotEmpty()
    }

    fun clearSelectedItems() {
        _selectedItems.clear()
        _isInEditMode.value = false
    }

    fun selectAllEmails(emailListSize: Int) {
        _selectedItems.clear()
        for (i in 0 until emailListSize) {
            _selectedItems.add(i)
        }
        _isInEditMode.value = _selectedItems.isNotEmpty()
    }

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun moveToArchived(userId: String, emailIds: List<String>, emailType: String, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        isLoading = true
        errorMessage = null

        val request = MoveEmailsRequest(emailIds, emailType)

        ApiClient.authService.getMoveToArchived(userId, request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isLoading = false
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = "Erro ao arquivar emails"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isLoading = false
                errorMessage = t.message ?: "Erro desconhecido"
            }
        })
    }

    fun moveToTrash(userId: String, emailIds: List<String>, emailType: String, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        isLoading = true
        errorMessage = null

        val request = MoveEmailsRequest(emailIds, emailType)
        ApiClient.authService.getMoveToTrash(userId, request).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isLoading = false
                if (response.isSuccessful){
                    onSuccess()
                }else {
                    errorMessage = "Erro ao arquivar emails"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isLoading = false
                errorMessage = t.message ?: "Erro desconhecido"
            }
        })
    }

    fun moveFromArchived(userId: String, emailIds: List<String>, onSuccess: () -> Unit, onError: (Throwable) -> Unit){
        isLoading = true
        errorMessage = null
        val request = MoveArchivedRequest(emailIds)

        ApiClient.authService.moveFromArchived(userId, request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isLoading = false
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = "erro ao desarquivar email"
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                isLoading = false
                errorMessage = t.message ?: "Erro desconhecido"
            }
        })
    }

    fun moveFromTrash(userId: String, emailIds: List<String>, onSuccess: () -> Unit, onError: (Throwable) -> Unit){
        isLoading = true
        errorMessage = null
        val request = MoveArchivedRequest(emailIds)

        ApiClient.authService.moveFromTrash(userId, request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isLoading = false
                if (response.isSuccessful){
                    onSuccess()
                }else {

                    errorMessage = "erro ao desarquivar email"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isLoading = false
                errorMessage = t.message ?: "Erro desconhecido"
            }
        })
    }
}