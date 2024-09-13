package br.com.fiap.email.screens

import android.os.Message
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.components.DialogLoading
import br.com.fiap.email.components.FormatTime
import br.com.fiap.email.models.DeleteEmailsRequest
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.MessageState
import br.com.fiap.email.viewmodel.ThemeViewModel
import br.com.fiap.email.viewmodel.UserViewModel

@Composable
fun TrashScreen(valController: NavController, userViewModel: UserViewModel, themeViewModel: ThemeViewModel){
    val userId = userViewModel.userId.observeAsState("")
    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    val colors = MaterialTheme.colorScheme
    val listTrashEmails by userViewModel.trashEmails.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }
    var showBottomSheet by remember { mutableStateOf(false) }

    val loading by userViewModel.loadingList
    val messageState by userViewModel.message

    LaunchedEffect(Unit) {
        userViewModel.fetchTrashEmails(userId.value)
    }

    Column {
        Column(
            modifier = Modifier.background(colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
                                tint = colors.onPrimary
                            )
                        }
                        Row {
                            IconButton(onClick = {
                                val selectedEmails = listEmailViewModel.selectedItems.map { index -> listTrashEmails[index].emailId }
                                showDialog = true
                                dialogMessage = "Voltando e-mails"

                                listEmailViewModel.moveFromTrash(
                                    userId = userId.value,
                                    emailIds = selectedEmails,
                                    onSuccess = {
                                        dialogMessage = "Movidos para emails!"
                                        listEmailViewModel.clearSelectedItems()
                                        userViewModel.fetchTrashEmails(userId.value)
                                        showDialog = false
                                    },
                                    onError = {
                                        dialogMessage = "Erro ao desfazer!"
                                        showDialog = false
                                    }
                                )
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.refresh),
                                    contentDescription = "botao de pastas",
                                    tint = colors.onPrimary
                                )
                            }
                            IconButton(onClick = {showBottomSheet = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more),
                                    contentDescription = "botao de mais",
                                    tint = colors.onPrimary
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
                                tint = colors.onPrimary,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.home_trash),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onPrimary,
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
                is MessageState.Success-> {
                    println("Emails carregados com sucesso!")
                }
                is MessageState.Error -> {
                    val errorMessage = (messageState as MessageState.Error).message
                    Text(text = "Erro: $errorMessage", color = Color.Red)
                }
                is MessageState.Loading -> {
                    Text(text = "Carregando...")
                }
                is MessageState.None -> {
                }

                null -> {
                }
            }

            if (listTrashEmails.isEmpty() && !loading) {
                Text(text = "A lixeira está vazia", color = Color.Gray, fontSize = 22.sp)
            }

            if(!loading){
                LazyColumn (
                    modifier = Modifier.fillMaxSize()
                ){
                    items(listTrashEmails.size) { index ->
                        val isSelected = listEmailViewModel.selectedItems.contains(index)
                        ListEmail(
                            email = listTrashEmails[index].emailDataBase.sentEmail ?: listTrashEmails[index].emailDataBase.receiveEmail ?: "",
                            name = listTrashEmails[index].emailDataBase.sentNome ?: listTrashEmails[index].emailDataBase.receiveNome ?: "",
                            body = listTrashEmails[index].emailDataBase.body ?: "",
                            subject = listTrashEmails[index].emailDataBase.subject ?: "",
                            time = FormatTime(listTrashEmails[index].emailDataBase.sentAt ?: listTrashEmails[index].emailDataBase.receivedAt ?: ""),
                            index = index,
                            isSelected = isSelected,
                            onItemSelected = { listEmailViewModel.toggleItemSelected(index)},
                            valController = valController
                        )
                    }
                }
            }
        }
        BottomSheetButton(
            showBottomSheet = showBottomSheet,
            onButtonClick = { showBottomSheet = it },
            listEmailViewModel = listEmailViewModel,
            userViewModel = userViewModel,
            themeViewModel = themeViewModel
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetButton(
    showBottomSheet: Boolean,
    onButtonClick: (Boolean) -> Unit,
    listEmailViewModel: ListEmailViewModel,
    userViewModel: UserViewModel,
    themeViewModel: ThemeViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    val azul_escuro: Color = colorResource(id = R.color.azul_escuro)
    val colors = MaterialTheme.colorScheme
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }
    val trashEmails by userViewModel.trashEmails.observeAsState(emptyList())
    val userId = userViewModel.userId.observeAsState("")
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

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
                    Card(
                        onClick = {
                            listEmailViewModel.selectAllEmails(trashEmails.size)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ){
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(painter = painterResource(id = R.drawable.add), contentDescription = "selecionar todos", tint = colors.onBackground)
                            Text(
                                text = stringResource(id = R.string.visualization_select),
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
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if(isDarkTheme){
                            Image(
                                painter = painterResource(id = R.drawable.spam),
                                contentDescription = "spam"
                            )
                        }else{
                            Image(
                                painter = painterResource(id = R.drawable.spam_white),
                                contentDescription = "spam"
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.visualization_spam),
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
                                trashEmails[index].emailId
                            }
                            showDialog = true
                            dialogMessage = "Deletando emails"

                            userViewModel.deleteEmailsFromTrash(
                                userId = userId.value,
                                emailIds = selectedEmails,
                                onSuccess = {
                                    dialogMessage = "E-mails deletados com sucesso!"
                                    listEmailViewModel.clearSelectedItems()
                                    userViewModel.fetchTrashEmails(userId.value)
                                    showDialog = false
                                }, onError = {
                                    dialogMessage = "erro ao deletar emails"
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
                                text = stringResource(id = R.string.visualization_delete),
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
