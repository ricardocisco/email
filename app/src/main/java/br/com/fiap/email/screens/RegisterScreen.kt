package br.com.fiap.email.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.fiap.email.models.AuthResponse
import br.com.fiap.email.models.RegisterRequest
import br.com.fiap.email.network.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(valController: NavController, authService: AuthService, onRegisterSuccess: (AuthResponse) -> Unit){

    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nome,
            onValueChange = {nome = it},
            label = { Text("seu nome") }
        )
        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email@example.com") }
        )
        TextField(
            value = password,
            onValueChange = { password = it},
            label = { Text("sua senha") }
        )
        Button(onClick = { val registerRequest = RegisterRequest(nome, email, password)
        authService.register(registerRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.isSuccessful){
                    onRegisterSuccess(response.body()!!)
                } else {
                    Log.e("Register", "Erro na resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("Register", "Erro na resposta")
            }
        })}) {
            Text(text = "Registrar-se")
        }
    }
}