package br.com.fiap.email.screens

import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import br.com.fiap.email.components.DialogLoading
import br.com.fiap.email.components.FormatTime
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.MessageState
import br.com.fiap.email.viewmodel.UserViewModel


@Composable
fun SentScreen(valController: NavController, userViewModel: UserViewModel){

    val userId = userViewModel.userId.observeAsState("")

    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    val listSentEmails by userViewModel.sentEmails.observeAsState(emptyList())
    val colors = MaterialTheme.colorScheme

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }
    var showBottomSheet by remember { mutableStateOf(false) }

    val loading by userViewModel.loadingList
    val messageState by userViewModel.message

    LaunchedEffect(Unit) {
        userViewModel.fetchSentEmails(userId.value)
    }

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
                            IconButton(onClick = {
                                val selectedEmails = listEmailViewModel.selectedItems.map { index ->
                                    listSentEmails[index].emailId
                                }
                                showDialog = true
                                dialogMessage = "Arquivando e-mails..."

                                listEmailViewModel.moveToArchived(
                                    userId = userId.value,
                                    emailIds = selectedEmails,
                                    emailType = "sent",
                                    onSuccess = {
                                        dialogMessage = "E-mails arquivados com sucesso!"
                                        listEmailViewModel.clearSelectedItems()
                                        userViewModel.fetchSentEmails(userId.value)
                                        showDialog = false
                                    },
                                    onError = {
                                        dialogMessage = "Erro ao arquivar e-mails."
                                        showDialog = false
                                    }
                                )
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.folder),
                                    contentDescription = "botao de pastas",
                                    tint = colors.onBackground
                                )
                            }
                            IconButton(onClick = { showBottomSheet = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more),
                                    contentDescription = "botao de mais",
                                    tint = colors.onBackground
                                )
                            }
                            DialogLoading(
                                showDialog = showDialog,
                                onDismiss = {showDialog = true},
                                message = dialogMessage,
                                isLoading = listEmailViewModel.isLoading,
                                delayTime = 9000
                            )
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
                            modifier = Modifier
                                .offset(x = (-110).dp)
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
                            modifier = Modifier
                                .offset(x = (-20).dp)
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
            when(messageState){
                is MessageState.Success -> {
                    println("emails carregados com sucesso!")
                }
                is MessageState.Error -> {
                    val errorMessage = (messageState as MessageState.Error).message
                    Text(text = "Erro ao carregar os emails: ${errorMessage}")
                }
                is MessageState.Loading -> {
                    Text(text = "Carregando...")
                }
                is MessageState.None -> {

                }
                null -> {

                }
            }

            if(listSentEmails.isEmpty() && !loading){
                Text(text = "A caixa está vazia!")
            }
            if(!loading){
                LazyColumn (
                    modifier = Modifier.fillMaxSize()
                ){
                    items(listSentEmails.size) { index ->
                        val isSelected = listEmailViewModel.selectedItems.contains(index)
                        ListEmail(
                            email = listSentEmails[index].sentEmail,
                            name = listSentEmails[index].sentNome,
                            body = listSentEmails[index].body,
                            subject = listSentEmails[index].subject,
                            time = FormatTime(listSentEmails[index].sentAt) ?: "",
                            index = index,
                            isSelected = isSelected,
                            onItemSelected = {listEmailViewModel.toggleItemSelected(index)},
                            valController = valController
                        )
                    }
                }
            }
        }
        BottomSheetButtonEdit(
            showBottomSheet = showBottomSheet,
            onButtonClick = { showBottomSheet = it },
            listEmailViewModel = listEmailViewModel,
            userViewModel = userViewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListEmail(
    email: String,
    name: String,
    body: String,
    subject: String,
    time: String,
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
                    onClick = { valController.navigate("emailDetail/${name}/${email}/${body}/${subject}/${time}") },
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
                        Text(
                            text = name,
                            color = if (isSelected) colors.surface else colors.onPrimary,
                            fontSize = 16.sp
                        )
                        Text(
                            text = email,
                            color = if (isSelected) colors.surface else colors.onPrimary
                        )
                    }
                    Column {
                        Text(
                            text = time,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetButtonEdit(
    showBottomSheet: Boolean,
    onButtonClick: (Boolean) -> Unit,
    listEmailViewModel: ListEmailViewModel,
    userViewModel: UserViewModel,
) {
    val sheetState = rememberModalBottomSheetState()
    val azul_escuro: Color = colorResource(id = R.color.azul_escuro)
    val colors = MaterialTheme.colorScheme
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }
    val sentEmails by userViewModel.sentEmails.observeAsState(emptyList())
    val userId = userViewModel.userId.observeAsState("")

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
                ) {
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
                            modifier = Modifier.padding(start = 10.dp),
                            color = colors.onPrimary
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Card(
                        onClick = {
                            val selectedEmails = listEmailViewModel.selectedItems.map { index ->
                                sentEmails[index].emailId
                            }
                            showDialog = true
                            dialogMessage = "Deletando emails"

                            listEmailViewModel.moveToTrash(
                                userId = userId.value,
                                emailIds = selectedEmails,
                                emailType = "received",
                                onSuccess = {
                                    dialogMessage = "E-mails deletados com sucesso!"
                                    listEmailViewModel.clearSelectedItems()
                                    userViewModel.fetchReceivedEmails(userId.value)
                                    showDialog = false
                                },
                                onError = {
                                    dialogMessage = "Erro ao deletar e-mails."
                                    showDialog = false
                                }
                            )

                            onButtonClick(true)
                        },
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        modifier = Modifier.fillMaxWidth()
                    ){
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
                    }
                    LaunchedEffect(listEmailViewModel.isLoading) {
                        if (!listEmailViewModel.isLoading) {
                            showDialog = false
                            onButtonClick(true)
                        }
                    }
                    DialogLoading(
                        showDialog = showDialog,
                        onDismiss = {showDialog = true},
                        message = dialogMessage,
                        isLoading = listEmailViewModel.isLoading,
                        delayTime = 7000
                    )
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