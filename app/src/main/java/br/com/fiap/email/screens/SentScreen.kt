package br.com.fiap.email.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.UserViewModel


@Composable
fun SentScreen(valController: NavController, userViewModel: UserViewModel){

    val userId = userViewModel.userId.observeAsState("")
    LaunchedEffect(Unit) {
        userViewModel.fetchSentEmails(userId.value)
    }
    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    val listSentEmails by userViewModel.sentEmails.observeAsState(emptyList())
    val colors = MaterialTheme.colorScheme

    Column {
        Column {
            Box (
                modifier = Modifier
                    .height(80.dp)
            ) {
                if (isInEditMode) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { listEmailViewModel.clearSelectedItems() }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Pesquisa",
                                tint = colors.onBackground
                            )
                        }
                        Row {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.folder),
                                    contentDescription = "botao de pastas",
                                    tint = colors.onBackground
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more),
                                    contentDescription = "botao de mais",
                                    tint = colors.onBackground
                                )
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {valController.navigate("homeApp")},
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
                            text = "Enviados",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground,
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
            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ){
                items(listSentEmails.size) { index ->
                    val isSelected = listEmailViewModel.selectedItems.contains(index)
                    ListEmail(
                        email = listSentEmails[index].sentEmail,
                        nome = listSentEmails[index].sentNome,
                        body = listSentEmails[index].body,
                        subject = listSentEmails[index].subject,
                        index = index,
                        isSelected = isSelected,
                        onItemSelected = {emailIndex -> listEmailViewModel.toggleItemSelected(index)},
                        valController = valController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListEmail(
    email: String,
    nome: String,
    body: String,
    subject: String,
    index: Int,
    isSelected: Boolean,
    onItemSelected: (Int) -> Unit,
    valController: NavController
) {

    val context = LocalContext.current
    val customAzulClaro = colorResource(id = R.color.customAzulClaro)

    val colors = MaterialTheme.colorScheme

    ElevatedCard(
        onClick = {},
        modifier = Modifier
            .padding(
                horizontal = 5.dp,
                vertical = 1.dp
            )
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        vibrate(context)
                        onItemSelected(index)
                    }
                )
                .background(if (isSelected) customAzulClaro else colors.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .width(50.dp)
                        .clip(shape = CircleShape)
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        androidx.wear.compose.material.Text(
                            text = nome,
                            color = if (isSelected) colors.surface else colors.onPrimary,
                            fontSize = 16.sp
                        )
                        androidx.wear.compose.material.Text(
                            text = email,
                            color = if (isSelected) colors.surface else colors.onPrimary
                        )
                    }
                }

            }
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(bottom = 15.dp),
                    text = subject,
                    color = if (isSelected) colors.surface else colors.onPrimary,
                    fontSize = 16.sp
                )
                androidx.wear.compose.material.Text(
                    text = body,
                    color = if (isSelected) colors.surface else colors.onPrimary
                )
            }
        }
    }
}