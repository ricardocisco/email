package br.com.fiap.email.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.network.AuthService
import br.com.fiap.email.network.ConnectivityObserver
import br.com.fiap.email.viewmodel.AuthViewModel
import br.com.fiap.email.viewmodel.ThemeViewModel
import br.com.fiap.email.viewmodel.UserViewModel

@Composable
fun LoginScreen(userViewModel: UserViewModel, valController: NavController, authService: AuthService, authViewModel: AuthViewModel, themeViewModel: ThemeViewModel, onLoginSuccess: () -> Unit){

    val colors = MaterialTheme.colorScheme
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
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
                text = stringResource(id = R.string.hello_world),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)
            )
            Image(
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp),
                painter = painterResource(id = R.drawable.hand),
                contentDescription = "Hand"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "E-mail",
            fontWeight = FontWeight.Bold,
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Digite seu e-mail") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp),

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Senha",
            fontWeight = FontWeight.Bold,
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) {
                    painterResource(id = R.drawable.visibility)
                } else {
                    painterResource(id = R.drawable.visibilityoff)
                }

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = image,
                        contentDescription = if (passwordVisible) "Esconder senha" else "Mostrar senha"
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween
        ){
            Row {
                Text(text = "Lembre-se")
            }
            Text(modifier = Modifier
                .clickable {
                    valController.navigate("resetScreen")
                }, text = stringResource(id = R.string.forgot_pass)
            )
        }
        Button(
            onClick = {
                authViewModel.loginUser(
                    email = email,
                    password = password,
                    onLoginSuccess = { authResponse ->
                        userViewModel.setUserName(authResponse.user.nome)
                        userViewModel.setUserId(authResponse.user.id)
                        userViewModel.setUserEmail(authResponse.user.email)
                        userViewModel.setUserTheme(authResponse.user.preferences.theme)
                        themeViewModel.setLocalFontSize(authResponse.user.preferences.fontSize)
                        themeViewModel.setLocalLanguage(authResponse.user.preferences.language)
                        onLoginSuccess()
                        },
                    onError = { errorMessage ->
                        mensagem = errorMessage
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
                text = stringResource(id = R.string.login_button),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp
            )
        }
        errorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.or_login),
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
            Text(
                text = stringResource(id = R.string.not_register),
                color = Color(0xFF000000),
                fontSize = 18.sp
            )
            Text(
                text = stringResource(id = R.string.on_register),
                modifier = Modifier
                    .clickable {
                        valController.navigate("register")
                    }
                    .padding(start = 6.dp),
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
    ConnectivityObserver()
}