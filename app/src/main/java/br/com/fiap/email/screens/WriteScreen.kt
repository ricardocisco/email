package br.com.fiap.email.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.email.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(valController: NavController) {

    val customBlue: Color = colorResource(id = R.color.customBlue)
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)
    val customCinza: Color = colorResource(id = R.color.customCinza)
    val customDarkCinza: Color = colorResource(id = R.color.customDarkCinza)
    var campoDe by rememberSaveable { mutableStateOf("") }
    var campoPara by rememberSaveable { mutableStateOf("") }
    var campoAssunto by rememberSaveable { mutableStateOf("") }
    var campoEmail by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
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
                        tint = Color.Black,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
                Text(
                    text = "Escrever",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(customBlue, customCinza),
                            startY = 0f,
                            endY = 120f,
                        )
                    )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(customCinza),
            verticalArrangement = Arrangement.SpaceBetween,
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
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedPlaceholderColor = Color.LightGray,
                    cursorColor = customBlue,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 30.dp)
                //.border(width = 1.dp, Color.Black)
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
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
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
                            tint = Color.Black,
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 30.dp)
                //.border(width = 1.dp, Color.Black)
            )
            Spacer(modifier = Modifier.height(40.dp))
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
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
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
                maxLines = 80,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = customBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            )
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(40.dp)
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors = listOf(customBlue, customCinza),
//                            startY = 120f,
//                            endY = 0f,
//                        )
//                    )
//            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(Color.White)
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
                        onClick = { },
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
                }
            }
        }

    }
}