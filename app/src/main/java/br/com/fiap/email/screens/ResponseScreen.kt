package br.com.fiap.email.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.viewmodel.UserViewModel

@Composable
fun ResponseScreen(valController: NavController, name: String, email: String, subject: String, userViewModel: UserViewModel) {
    val deMail by userViewModel.userEmail.observeAsState("")
    val user by userViewModel.userId.observeAsState("")
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val customBlue: Color = colorResource(id = R.color.customBlue)
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)
    val customDarkCinza: Color = colorResource(id = R.color.customDarkCinza)
    var campoDe by rememberSaveable { mutableStateOf("${deMail}") }
    var campoPara by rememberSaveable { mutableStateOf("${email}") }
    var campoAssunto by rememberSaveable { mutableStateOf("${subject}") }
    var campoEmail by rememberSaveable { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surface)
        ) {
            Column {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    IconButton(
                        onClick = { valController.navigate("homeApp") },
                        modifier = Modifier
                            .offset(x = (-170).dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.seta_voltar),
                            contentDescription = "Botão de Voltar",
                            tint = colors.onPrimary,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                    }
                    Text(
                        text = "Responder",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.85f)
                    .background(colors.surface),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                ) {
                    TextField(
                        value = campoDe,
                        onValueChange = { campoDe = it },
                        label = { Text("De:") },
                        placeholder = { Text("exemplo@email.com") },
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = colors.onPrimary,
                            unfocusedTextColor = colors.onPrimary,
                            focusedLabelColor = colors.onPrimary,
                            unfocusedLabelColor = colors.onPrimary,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = customBlue,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                    )
                    TextField(
                        value = campoPara,
                        onValueChange = { campoPara = it },
                        label = { Text("Para:") },
                        placeholder = { Text("exemplo@email.com") },
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        ),
                        maxLines = 10,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = colors.onPrimary,
                            unfocusedTextColor = colors.onPrimary,
                            focusedLabelColor = colors.onPrimary,
                            unfocusedLabelColor = colors.onPrimary,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = customBlue,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = { /*TODO*/ },
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.seta_baixo),
                                    contentDescription = "Botão p/ Baixo",
                                    tint = colors.onPrimary,
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                    )
                    TextField(
                        value = campoAssunto,
                        onValueChange = { campoAssunto = it },
                        label = { Text("Assunto:") },
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = colors.onPrimary,
                            unfocusedTextColor = colors.onPrimary,
                            focusedLabelColor = colors.onPrimary,
                            unfocusedLabelColor = colors.onPrimary,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = customBlue,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 30.dp)
                    )
                    TextField(
                        value = campoEmail,
                        onValueChange = { campoEmail = it },
                        label = { Text("E-mail:") },
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        ),
                        maxLines = 5,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = colors.onPrimary,
                            unfocusedTextColor = colors.onPrimary,
                            focusedLabelColor = colors.onPrimary,
                            unfocusedLabelColor = colors.onPrimary,
                            cursorColor = customBlue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                    )
                }
            }
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colors.surface)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .height(45.dp)
                        .width(170.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = customDarkCinza)
                ) {
                    Icon(
                        painterResource(id = R.drawable.anexo),
                        contentDescription = "Anexo",
                        tint = Color.Black,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Text(
                        text = "Anexar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        if (user.isNotBlank() && campoPara.isNotBlank() && campoAssunto.isNotBlank() && campoEmail.isNotBlank()) {
                            userViewModel.sendEmail(user, campoPara, campoAssunto, campoEmail)
                            dialogMessage = "E-mail enviado com sucesso!"
                            campoPara = ""
                            campoAssunto = ""
                            campoEmail = ""
                        } else {
                            dialogMessage = "Preencha todos os campos"
                        }
                        showDialog = true
                    },
                    modifier = Modifier
                        .height(45.dp)
                        .width(170.dp)
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(15.dp),
                            spotColor = customDarkBlue,
                            clip = false
                        ),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = customDarkBlue)
                ) {
                    Icon(
                        painterResource(id = R.drawable.envio),
                        contentDescription = "Envio",
                        tint = Color.White,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Enviar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
                if (showDialog) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(4000)
                        showDialog = false
                    }

                    Dialog(onDismissRequest = { showDialog = false }) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(100.dp)
                                .width(250.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = dialogMessage,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

    }
}