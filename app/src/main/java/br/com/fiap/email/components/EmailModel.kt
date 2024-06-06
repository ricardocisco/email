package br.com.fiap.email.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import kotlinx.coroutines.launch


@Composable
fun EmailModel(valController: NavController, name: String, email: String) {

    var showBottomSheet by remember { mutableStateOf(false) }

    val gray_100: Color = colorResource(id = R.color.gray_100)
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .shadow(1.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = { valController.navigate("homeApp") }) {
                Icon(
                    painterResource(id = R.drawable.seta_voltar),
                    contentDescription = "Botão de Voltar",
                    tint = Color.Black,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                )
            }
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.folder),
                        contentDescription = "botao de pastas"
                    )
                }
                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = "botao de mais"
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
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
                        Text(text = "$name", fontSize = 18.sp)
                        Text(text = "$email", fontSize = 13.sp)
                    }
                    Text(text = "1h ago")
                }
            }
            Column {
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "What is your opinion about this boss?", fontSize = 18.sp)
                }
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                                "Sit amet consectetur adipiscing elit duis tristique. Enim lobortis scelerisque fermentum dui faucibus in ornare. Faucibus vitae aliquet nec ullamcorper sit amet risus nullam. " +
                                "Risus feugiat in ante metus dictum. Iaculis at erat pellentesque adipiscing commodo elit at imperdiet. Adipiscing tristique risus nec feugiat in. Nibh praesent tristique magna sit amet purus gravida. " +
                                "Egestas diam in arcu cursus euismod quis viverra nibh cras. Tincidunt nunc pulvinar sapien et ligula ullamcorper. Rhoncus mattis rhoncus urna neque viverra justo nec. Quisque non tellus orci ac. " +
                                "Cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris. " +
                                "Sed tempus urna et pharetra.", fontSize = 14.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {},
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
                    Text(text = "Encaminhar", modifier = Modifier.padding(start = 10.dp), color = Color.Black, fontSize = 16.sp)
                }
                Button(
                    onClick = {valController.navigate("responseEmail/${name}/${email}")},
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
                        text = "Responder",
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

    Column {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    onButtonClick(false)
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .padding(25.dp)
                ){
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.replyblack),
                            contentDescription = "reply"
                        )
                        Text(
                            text = "Responder",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.forward),
                            contentDescription = "forward"
                        )
                        Text(
                            text = "Encaminhar",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.folderimg),
                            contentDescription = "pastas"
                        )
                        Text(
                            text = "Adicionar a Pasta",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
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
                            text = "Denunciar Spam",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
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
                            text = "Deletar",
                            modifier = Modifier.padding(start = 10.dp),
                            color = azul_escuro
                        )
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

