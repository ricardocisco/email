package br.com.fiap.email.screens


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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.components.DialogLoading
import br.com.fiap.email.components.FormatTime
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.MessageState
import br.com.fiap.email.viewmodel.ThemeViewModel
import br.com.fiap.email.viewmodel.UserViewModel

@Composable
fun ArchivedScreen(valController: NavController, userViewModel: UserViewModel, themeViewModel: ThemeViewModel){

    val userId = userViewModel.userId.observeAsState("")
    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    val listArchivedEmails by userViewModel.archivesEmails.observeAsState(emptyList())
    val colors = MaterialTheme.colorScheme

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }
    var showBottomSheet by remember { mutableStateOf(false) }

    val loading by userViewModel.loadingList
    val messageState by userViewModel.message

    LaunchedEffect(Unit) {
        userViewModel.fetchArchivedEmails(userId.value)
    }

    Column {
        Column(
            modifier = Modifier.background(color = colors.background),
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
                                tint = colors.onBackground
                            )
                        }
                        Row {
                            IconButton(onClick = {
                                val selectedEmails = listEmailViewModel.selectedItems.map { index -> listArchivedEmails[index].emailId }
                                showDialog = true
                                dialogMessage = "Desarquivando e-mails..."

                                listEmailViewModel.moveFromArchived(
                                    userId = userId.value,
                                    emailIds = selectedEmails,
                                    onSuccess = {
                                        dialogMessage = "E-mails desarquivados com sucesso!"
                                        listEmailViewModel.clearSelectedItems()
                                        userViewModel.fetchArchivedEmails(userId.value)
                                        showDialog = false
                                    },
                                    onError = {
                                        dialogMessage = "Erro ao desarquivar e-mails."
                                        showDialog = false
                                    }
                                )
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.refresh),
                                    contentDescription = "botao de pastas",
                                    tint = colors.onBackground
                                )
                            }
                            IconButton(onClick = {showBottomSheet = true }) {
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
                                tint = colors.onPrimary,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.home_archived),
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
            
            if(listArchivedEmails.isEmpty() && !loading){
                Text(text = "A caixa está vazia", color = Color.Gray, fontSize = 22.sp)
            }
            
            if(!loading){
                LazyColumn (
                    modifier = Modifier.fillMaxSize()
                ){
                    items(listArchivedEmails.size) { index ->
                        val isSelected = listEmailViewModel.selectedItems.contains(index)
                        ListEmail(
                            email = listArchivedEmails[index].emailDataBase.sentEmail ?: listArchivedEmails[index].emailDataBase.receiveEmail ?: "",
                            name = listArchivedEmails[index].emailDataBase.sentNome ?: listArchivedEmails[index].emailDataBase.receiveNome ?: "",
                            body = listArchivedEmails[index].emailDataBase.body ?: "",
                            subject = listArchivedEmails[index].emailDataBase.subject ?: "",
                            time = FormatTime(listArchivedEmails[index].emailDataBase.sentAt ?: listArchivedEmails[index].emailDataBase.receivedAt ?: ""),
                            index = index,
                            isSelected = isSelected,
                            onItemSelected = {listEmailViewModel.toggleItemSelected(index)},
                            valController = valController
                        )
                    }
                }
            }
        }
        BottomSheetButtonArchived(
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
fun BottomSheetButtonArchived(
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
    val archivedEmails by userViewModel.archivesEmails.observeAsState(emptyList())
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
                            listEmailViewModel.selectAllEmails(archivedEmails.size)
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
                                archivedEmails[index].emailId
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



