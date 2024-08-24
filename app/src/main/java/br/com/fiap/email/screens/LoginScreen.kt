package br.com.fiap.email.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.ButtonDefaults
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.LoginRequest
import br.com.fiap.email.network.ApiClient
import br.com.fiap.email.network.AuthService
import br.com.fiap.email.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(userViewModel: UserViewModel, valController: NavController, authService: AuthService, onLoginSuccess: () -> Unit){

    val colors = MaterialTheme.colorScheme
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email@example.com") }
        )
        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("sua senha") }
        )
        androidx.wear.compose.material.Button(
            onClick = { val loginRequest = LoginRequest(email, password)
                      ApiClient.authService.login(loginRequest).enqueue(object :  Callback<AuthResponse> {
                          override fun onResponse(
                              call: Call<AuthResponse>,
                              response: Response<AuthResponse>
                          ) {
                              if (response.isSuccessful) {
                                  val authResponse = response.body()
                                  if( authResponse?.user != null){
                                      userViewModel.setUserName(authResponse.user.nome)
                                      onLoginSuccess()
                                  }
                                  Log.d("Login", "AuthResponse: $authResponse")
                              } else {
                                  Log.e("Login", "Erro na resposta: ${response.code()}")
                              }
                          }
                          override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                              Log.e("Login", "Erro na requisição: ${t.message}")
                          }
                      })},
            modifier = Modifier
                .height(45.dp)
                .width(170.dp),
            colors = androidx.wear.compose.material.ButtonDefaults.buttonColors(backgroundColor = colors.onBackground)
        ) {
            androidx.wear.compose.material.Text(text = "Entrar", color = colors.primary)
        }
    }
}