package br.com.fiap.email.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text

@Composable
fun InicialScreen(valController: NavController){

    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        Text(text = "Email", color = Color.Black, fontSize = 32.sp)
        Button(
            onClick = {valController.navigate("login")},
            modifier = Modifier
                .height(45.dp)
                .width(170.dp)
                .padding(bottom = 10.dp)
        ) {
            Text(text = "Login")
        }
        Button(
            onClick = { valController.navigate("register")},
            modifier = Modifier
                .height(45.dp)
                .width(170.dp)
                .padding(bottom = 10.dp)
        ) {
            Text(text = "Register")
        }
    }
}