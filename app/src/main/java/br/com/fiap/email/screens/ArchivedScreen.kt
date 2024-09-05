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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.components.DialogLoading
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.UserViewModel

@Composable
fun ArchivedScreen(valController: NavController, userViewModel: UserViewModel){

    val userId = userViewModel.userId.observeAsState("")
    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    val listArchivedEmails by userViewModel.archivesEmails.observeAsState(emptyList())
    val colors = MaterialTheme.colorScheme

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("Processando...") }

    LaunchedEffect(Unit) {
        userViewModel.fetchArchivedEmails(userId.value)
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
                            text = "Arquivados",
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
                items(listArchivedEmails.size) { index ->
                    val isSelected = listEmailViewModel.selectedItems.contains(index)
                    ListEmail(
                        email = listArchivedEmails[index].emailDataBase.sentEmail ?: listArchivedEmails[index].emailDataBase.receiveEmail ?: "",
                        name = listArchivedEmails[index].emailDataBase.sentNome ?: listArchivedEmails[index].emailDataBase.receiveNome ?: "",
                        body = listArchivedEmails[index].emailDataBase.body ?: "",
                        subject = listArchivedEmails[index].emailDataBase.subject ?: "",
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


