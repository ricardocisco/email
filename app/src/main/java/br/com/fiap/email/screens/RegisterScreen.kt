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

@Composable
fun RegisterScreen(valController: NavController, authService: AuthService, onRegisterSuccess: (AuthResponse) -> Unit){


    val colors = MaterialTheme.colorScheme
    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                text = "Cadastro! :)",
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
            text = "Crie uma senha",
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
            onClick = { val registerRequest = RegisterRequest(nome, email, password)
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
                })},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2F2F2F))
        ) {
            Text(
                text = "Cria conta",
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
            Text(text = "Ou registre-se com ... ",
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
            Text(text = "Você já possui uma conta ?",
                color = Color(0xFF000000),
                fontSize = 18.sp
            )
            Text(
                text = "Faça Login",
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
