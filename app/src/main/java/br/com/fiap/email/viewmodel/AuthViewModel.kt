package br.com.fiap.email.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.LoginRequest
import br.com.fiap.email.models.PasswordReset
import br.com.fiap.email.models.RegisterRequest
import br.com.fiap.email.network.ApiClient
import br.com.fiap.email.network.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel() : ViewModel(){

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoginSuccessful by mutableStateOf(false)
    var registerSuccessful by mutableStateOf(false)
    var successMessage: String? by mutableStateOf(null)

    fun loginUser(email: String, password: String, onLoginSuccess: (AuthResponse) -> Unit, onError: (String) -> Unit){

        if(email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, preencha todos os campos."
            return
        }

        isLoading = true
        errorMessage = null

        val loginRequest = LoginRequest(email, password)

        ApiClient.authService.login(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>
            ) {
                isLoading = false
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if( authResponse?.user != null){
                        onLoginSuccess(authResponse)
                        isLoginSuccessful = true
                    }
                    else{
                        errorMessage = "Usuário não encontrado."
                    }
                }else {
                    handleErrorResponse(response)
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                isLoading = false
                errorMessage = "Erro na requisição: ${t.localizedMessage}"
                onError("Erro na requisição: ${t.localizedMessage}")
            }
        })
    }

    fun registerUser(nome: String, email: String, password: String, onRegisterSuccess: (AuthResponse) -> Unit, onError: (String) -> Unit){

        if(nome.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, preencha todos os campos."
            return
        }
        isLoading = true
        errorMessage = null

        val registerRequest = RegisterRequest(nome, email, password)

        ApiClient.authService.register(registerRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                isLoading = false
                if(response.isSuccessful){
                    onRegisterSuccess(response.body()!!)
                    registerSuccessful = true
                    errorMessage = "Usuario criado com sucesso!"
                } else {
                    handleErrorResponse(response)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                isLoading = false
                errorMessage = "Erro na requisição: ${t.localizedMessage}"
                onError("Erro na requisição: ${t.localizedMessage}")
            }
        })
    }

    fun passwordReset(email: String, newPassword: String, onSuccess: () -> Unit, onError: (String) -> Unit){
        if(email.isBlank() || newPassword.isBlank()){
            errorMessage = "Por favor, preencha todos os campos."
            return
        }
        isLoading = true
        errorMessage = null
        successMessage = null

        val passwordRequest = PasswordReset(email, newPassword)

        ApiClient.authService.resetPassword(passwordRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isLoading = false
                if(response.isSuccessful){
                    errorMessage = "Senha alterada com sucesso"
                    onSuccess()
                } else {
                    errorMessage = "As senhas não coincidem."
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isLoading = false
                onError("Erro na requisição: ${t.localizedMessage}")
            }
        })
    }

    private fun handleErrorResponse(response: Response<AuthResponse>) {
        val errorBody = response.errorBody()?.string()
        errorMessage = when (response.code()) {
            400 -> "Requisição inválida: $errorBody"
            401 -> "Não autorizado: $errorBody"
            403 -> "Proibido: $errorBody"
            404 -> "Não encontrado: $errorBody"
            500 -> "Erro no servidor: $errorBody"
            else -> "Erro desconhecido: $errorBody"
        }
    }

    fun clearErrorMessage() {
        errorMessage = null
        successMessage = null
    }


}