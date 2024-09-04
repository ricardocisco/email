package br.com.fiap.email.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.email.R
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
    var passwordVisible by remember { mutableStateOf(false) }

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
                text = "Olá, bem-vindo!  ",
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
        Row {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Check sign",
                tint = Color(0xFF000000)
            )
            Text(text = "Lembre-se")
        }
        Text(modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    valController.navigate("resetScreen")
                }, text = "Esqueceu sua senha?",)
        Button(
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
                                userViewModel.setUserId(authResponse.user.id)
                                userViewModel.setUserEmail(authResponse.user.email)
                                Log.d("Login", "${response.body()}")
                                onLoginSuccess()
                            }
                            else{
                                Log.d("Login", "Erro: ${response.code()}")
                            }
                        }else {
                            Log.d("Login", "Erro: ${response.code()}")
                            Log.d("Login", "Erro: ${response.body()}")
                            Log.d("Login", "Erro: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Log.e("Login", "Erro na requisição: ${t.message}")
                    }
                })},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2F2F2F))
        ) {
            Text(
                text = "Entrar",
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Ou entre com ... ",
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
                text = "Continuar com uma conta Google",
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
                text = "Continuar com uma conta Apple",
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
                text = "Não tem uma conta?",
                color = Color(0xFF000000),
                fontSize = 18.sp
            )
            Text(
                text = "Cadastre-se",
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

}