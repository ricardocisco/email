package br.com.fiap.email.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.navigation.Screens
import br.com.fiap.email.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch


@Composable
fun EmailModel(valController: NavController,themeViewModel: ThemeViewModel, name: String, email: String, body: String, subject: String, time: String) {

    var showBottomSheet by remember { mutableStateOf(false) }
    val gray_100: Color = colorResource(id = R.color.gray_100)
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

    val fontSize by themeViewModel.fontSize.collectAsState()

    val colors = MaterialTheme.colorScheme

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .shadow(1.dp)
                .background(colors.primary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = {valController.navigate("homeApp")}) {
                Icon(
                    painterResource(id = R.drawable.seta_voltar),
                    contentDescription = "Botão de Voltar",
                    tint = colors.onPrimary,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            Row {
                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = "botao de mais",
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp),
                        tint = colors.onPrimary
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
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    modifier = Modifier
                        .width(68.dp)
                        .clip(shape = CircleShape),
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Perfil"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = name, fontSize = 18.sp, color = colors.onPrimary)
                        Text(text = email, fontSize = 15.sp, color = colors.onPrimary)
                    }
                    Text(text = time, color = colors.onPrimary)
                }
            }
            Column(
                modifier = Modifier.fillMaxHeight(0.85f)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = subject, fontSize = fontSize.sp, color = colors.onPrimary)
                }
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = body, fontSize = fontSize.sp, color = colors.onPrimary
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {valController.navigate("writeEmail?subject=${subject}&body=${body}")},
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(15.dp),
                            clip = false
                        )
                        .size(170.dp, 45.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = gray_100)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.forward),
                        contentDescription = "forward",
                    )
                    Text(
                        text = stringResource(id = R.string.visualization_forward),
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp)
                }
                Button(
                    onClick = {valController.navigate("responseEmail/${name}/${email}/${subject}")},
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(15.dp),
                            spotColor = customDarkBlue,
                            clip = false
                        )
                        .size(170.dp, 45.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = customDarkBlue)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.reply),
                        contentDescription = "forward"
                    )
                    Text(
                        text = stringResource(id = R.string.visualization_reply),
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 16.sp
                    )
                }
            }
            BottomSheetButton(showBottomSheet) {
                showBottomSheet = it
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetButton(showBottomSheet: Boolean, onButtonClick: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val azul_escuro: Color = colorResource(id = R.color.azul_escuro)

    val colors = MaterialTheme.colorScheme
    var isEnabled = false

    Column {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    onButtonClick(false)
                },
                sheetState = sheetState,
                containerColor = colors.primary
            ) {
                Column(
                    modifier = Modifier
                        .padding(25.dp)
                ){
                    Card(
                        onClick = {isEnabled = false},
                        colors = CardDefaults.cardColors(containerColor = if (isEnabled) Color.Transparent else Color.Gray.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(if (isEnabled) 1f else 0.5f)
                            .clickable(enabled = isEnabled) {},
                        enabled = isEnabled) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.folder),
                                contentDescription = "spam"
                            )
                            Text(
                                text = stringResource(id = R.string.home_archived),
                                modifier = Modifier.padding(start = 10.dp),
                                color = colors.onPrimary
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Card(onClick = {isEnabled = false},
                        colors = CardDefaults.cardColors(containerColor = if (isEnabled) Color.Transparent else Color.Gray.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(if (isEnabled) 1f else 0.5f)
                            .clickable(enabled = isEnabled) {},
                        enabled = isEnabled)
                    {
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.spam),
                                contentDescription = "spam"
                            )
                            Text(
                                text = stringResource(id = R.string.visualization_spam),
                                modifier = Modifier.padding(start = 10.dp),
                                color = colors.onPrimary
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Card(
                        onClick = {isEnabled = false},
                        colors = CardDefaults.cardColors(containerColor = if (isEnabled) Color.Transparent else Color.Gray.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(if (isEnabled) 1f else 0.5f)
                            .clickable(enabled = isEnabled) {},
                        enabled = isEnabled)
                    {
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = "delete"
                            )
                            Text(
                                text = stringResource(id = R.string.visualization_delete),
                                modifier = Modifier.padding(start = 10.dp),
                                color = azul_escuro
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

