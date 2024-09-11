package br.com.fiap.email.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.email.R
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import br.com.fiap.email.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(valController: NavController, authService: AuthService, authViewModel: AuthViewModel ,onRegisterSuccess: (AuthResponse) -> Unit){


    val colors = MaterialTheme.colorScheme
    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    val errorMessage = authViewModel.errorMessage

    LaunchedEffect(Unit) {
        authViewModel.clearErrorMessage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(start = 22.dp, end = 22.dp),
        Arrangement.Center,
        Alignment.Start
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.register),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)
            )
        }
        Text(
            text = "Nome",
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            label = { Text("Digite seu nome") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "E-mail",
            fontWeight = FontWeight.Bold,
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            label = { Text("Digite seu e-mail") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.new_pass),
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            label = { Text(text = "minimo 8 caracteres")}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                authViewModel.registerUser(
                    nome = nome,
                    email = email,
                    password = password,
                    onRegisterSuccess = {
                        onRegisterSuccess
                        nome = ""
                        email = ""
                        password = ""
                    },
                    onError = {
                        errorMessage -> mensagem = errorMessage
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2F2F2F))
        ) {
            Text(
                text = stringResource(id = R.string.register_button),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp
            )
        }
        errorMessage?.let { 
            Text(text = it, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.or_register),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Foto Google"
            )
            Text(
                text = stringResource(id = R.string.google_button),
                fontWeight = FontWeight.Bold,
                color = colors.onPrimary
            )
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.apple),
                contentDescription = "Foto Apple"
            )
            Text(
                text = stringResource(id = R.string.apple_button),
                fontWeight = FontWeight.Bold,
                color = colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.not_login),
                color = Color(0xFF000000),
                fontSize = 18.sp
            )
            Text(
                text = stringResource(id = R.string.login_button),
                modifier = Modifier
                    .clickable {
                        valController.navigate("login")
                    }
                    .padding(start = 6.dp),
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
