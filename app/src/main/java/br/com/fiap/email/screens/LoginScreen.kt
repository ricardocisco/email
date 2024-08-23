package br.com.fiap.email.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LoginScreen(valController: NavController, onLoginSuccess: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Email@example.com") }
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("sua senha") }
        )
        Button(onClick = { onLoginSuccess() }) {
            Text(text = "Entrar")
        }
    }
}