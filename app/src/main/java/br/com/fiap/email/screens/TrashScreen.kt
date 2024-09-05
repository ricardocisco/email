package br.com.fiap.email.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.components.DialogLoading
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.UserViewModel

@Composable
fun TrashScreen(valController: NavController, userViewModel: UserViewModel){
    val userId = userViewModel.userId.observeAsState("")
    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    val colors = MaterialTheme.colorScheme
    val listTrashEmails by userViewModel.trashEmails.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }

    LaunchedEffect(Unit) {
        userViewModel.fetchTrashEmails(userId.value)
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
                            text = "Lixeira",
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
                items(listTrashEmails.size) { index ->
                    val isSelected = listEmailViewModel.selectedItems.contains(index)
                    ListEmail(
                        email = listTrashEmails[index].emailDataBase.sentEmail ?: listTrashEmails[index].emailDataBase.receiveEmail ?: "",
                        name = listTrashEmails[index].emailDataBase.sentNome ?: listTrashEmails[index].emailDataBase.receiveNome ?: "",
                        body = listTrashEmails[index].emailDataBase.body ?: "",
                        subject = listTrashEmails[index].emailDataBase.subject ?: "",
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
