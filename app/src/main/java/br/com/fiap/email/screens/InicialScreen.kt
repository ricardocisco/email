package br.com.fiap.email.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Text
import br.com.fiap.email.R

@Composable
fun InicialScreen(valController: NavController){

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.width(350.dp).height(350.dp),
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "Foto BG"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.text),
            contentDescription = "text"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(){
            Button(
                onClick = {valController.navigate("login")},
                colors = androidx.wear.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Entrar", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { valController.navigate("register")},
                shape = RoundedCornerShape(8.dp),
                colors = androidx.wear.compose.material.ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Criar conta", color = Color.Black)
            }
        }
    }
}
