package br.com.fiap.email.screens

import android.widget.Switch
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(valController: NavController) {

    var validacaoNotificacao by remember { mutableStateOf(true) }
    var validacaoSom by remember { mutableStateOf(true) }
    var validacaoAgrupamento by remember { mutableStateOf(true) }
    val customPink: Color = colorResource(id = R.color.customPink)
    val customBlue: Color = colorResource(id = R.color.customBlue)
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
            ) {
                IconButton(
                    onClick = {valController.navigate("homeApp")},
                    modifier = Modifier
                        .offset(x = (-170).dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Botão de Voltar",
                        tint = colors.onBackground,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
                Text(
                    text = "Configurações",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 30.dp, bottom = 15.dp)
                    //.border(width = 1.dp, Color.Black)
            ) {
                Text(
                    text = "Tema",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = -7.dp)
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
//                                .offset(x = (-170).dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.circulo),
                            contentDescription = "Tema White",
                            tint = Color.White,
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .border(
                                    width = 5.dp,
                                    color = customPink,
                                    shape = CircleShape
                                )
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
//                                .offset(x = (-170).dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.circulo),
                            contentDescription = "Tema Dark",
                            tint = Color.Black,
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .border(
                                    width = 5.dp,
                                    Color.Black,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.72f)
            ){
                item {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp, bottom = 40.dp)
                        //.border(width = 1.dp, Color.Black)
                    ) {
                        Column{
                            Text(
                                text = "Notificações",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = colors.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp)
                            )
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Receber notificações",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.onBackground,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp, top = 15.dp)
                                )
                                Switch(
                                    checked = validacaoNotificacao,
                                    onCheckedChange = {
                                        validacaoNotificacao = it
                                    },
                                    thumbContent = if (validacaoNotificacao) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedTrackColor = customBlue
                                    )
                                )
                            }
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Som",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.onBackground,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp, top = 15.dp)
                                )
                                Switch(
                                    checked = validacaoSom,
                                    onCheckedChange = {
                                        validacaoSom = it
                                    },
                                    thumbContent = if (validacaoSom) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedTrackColor = customBlue
                                    )
                                )
                            }
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Receber notificações",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.onBackground,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp, top = 15.dp)
                                )
                                Switch(
                                    checked = validacaoAgrupamento,
                                    onCheckedChange = {
                                        validacaoAgrupamento = it
                                    },
                                    thumbContent = if (validacaoAgrupamento) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedTrackColor = customBlue
                                    )
                                )
                            }
                        }
                    }
                }
                item {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                        //.border(width = 1.dp, Color.Black)
                    ) {
                        Column{
                            Text(
                                text = "Desempenho",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = colors.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp)
                            )
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Animações",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.onBackground,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp, top = 15.dp)
                                )
                                Switch(
                                    checked = validacaoNotificacao,
                                    onCheckedChange = {
                                        validacaoNotificacao = it
                                    },
                                    thumbContent = if (validacaoNotificacao) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedTrackColor = customBlue
                                    )
                                )
                            }
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Atualizações Automáticas",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.onBackground,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp, top = 15.dp)
                                )
                                Switch(
                                    checked = validacaoSom,
                                    onCheckedChange = {
                                        validacaoSom = it
                                    },
                                    thumbContent = if (validacaoSom) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedTrackColor = customBlue
                                    )
                                )
                            }
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Limitar Dados em Segundo Plano",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.onBackground,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp, top = 15.dp)
                                )
                                Switch(
                                    checked = validacaoAgrupamento,
                                    onCheckedChange = {
                                        validacaoAgrupamento = it
                                    },
                                    thumbContent = if (validacaoAgrupamento) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedTrackColor = customBlue
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .padding(start = 30.dp, top = 15.dp)
                    //.border(width = 1.dp, Color.Black)
                    .height(150.dp)
            ) {
                Column{
                    Text(
                        text = "Histórico",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 15.dp)
                    )
                    Button(
                        onClick = { },
                        modifier = Modifier

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
                            painterResource(id = R.drawable.lixeira),
                            contentDescription = "Lixeira",
                            tint = Color.White,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Limpar histórico",
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