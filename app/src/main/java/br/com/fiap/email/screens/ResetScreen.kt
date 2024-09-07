package br.com.fiap.email.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.viewmodel.AuthViewModel

@Composable
fun ResetScreen(valController: NavController, authViewModel: AuthViewModel){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf<String?>(null) }
    val errorMessage = authViewModel.errorMessage

    LaunchedEffect(Unit) {
        authViewModel.clearErrorMessage()
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .padding(all = 22.dp),
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            IconButton(
                onClick = {valController.navigate("login")},
                modifier = Modifier
                    .size(48.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Voltar",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = "Esqueceu sua senha?",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Não se preocupe! Isso acontece. Por favor, digite o email associado à sua conta.")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "E-mail",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Senha",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Confirmar senha",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = newPassword,
                onValueChange = {newPassword = it},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    Log.d("PasswordCheck", "password: $password, newPassword: $newPassword")
                    if(password == newPassword){
                        authViewModel.passwordReset(
                            email = email,
                            newPassword = newPassword,
                            onSuccess = {
                                email = ""
                                password = ""
                                newPassword = ""
                            },
                            onError = { errorMessage ->
                                mensagem = errorMessage
                            }
                        )
                    } else {
                        mensagem = "As senhas não coincidem."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 22.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF2F2F2F))
            ) {
                Text(
                    text = "Redefinir senha",
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp
                )
            }
            mensagem?.let { 
                Text(text = it)
            }
            if (authViewModel.successMessage != null) {
                Text(text = authViewModel.successMessage!!, color = Color.Green, fontSize = 16.sp)
            }
            errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

        }
        Column (
            modifier = Modifier.padding(22.dp)
        ){
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Lembrou a senha?",
                    color = Color(0xFF000000))
                Text(
                    text = " Faça o login :)",
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}