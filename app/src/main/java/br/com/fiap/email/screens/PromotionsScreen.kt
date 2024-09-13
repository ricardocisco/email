package br.com.fiap.email.screens

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import br.com.fiap.email.R
import br.com.fiap.email.components.FormatTime
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.MessageState
import br.com.fiap.email.viewmodel.UserViewModel
import io.github.serpro69.kfaker.Faker

@Composable
fun PromotionsScreen(
    listEmailViewModel: ListEmailViewModel,
    valController: NavController,
    searchText: String,
    userViewModel: UserViewModel
) {
    val receivedEmail by userViewModel.receivedEmails.observeAsState(emptyList())
    val userId = userViewModel.userId.observeAsState("")
    LaunchedEffect(Unit) {
        userViewModel.fetchReceivedEmails(userId.value)
    }

    val loading by userViewModel.loadingList
    val messageState by userViewModel.message

    val filteredEmailDataList = receivedEmail.filter {
        it.receiveNome?.contains(searchText, ignoreCase = true) == true || it.subject?.contains(
            searchText,
            ignoreCase = true
        ) == true
    }

    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .padding(top = 80.dp)
                .height(1.dp)
                .fillMaxWidth()
        )
        when(messageState){
            is MessageState.Success -> {
                println("emails carregados com sucesso!")
            }
            is MessageState.Error -> {
                val errorMessage = (messageState as MessageState.Error).message
                androidx.compose.material3.Text(text = "Erro ao carregar os emails: ${errorMessage}")
            }
            is MessageState.Loading -> {
                androidx.compose.material3.Text(text = "Carregando...")
            }
            is MessageState.None -> {

            }
            null -> {

            }
        }
        
        if(filteredEmailDataList.isEmpty() && !loading){
            Text(text = "A caixa está vazia", color = Color.Gray, fontSize = 22.sp)
        }

        if(!loading){
            LazyColumn(
                modifier = Modifier.padding(top = 85.dp)
            ) {
                items(filteredEmailDataList.size) { index ->
                    val isFavorite = listEmailViewModel.isFavorite(index)
                    val isSelected = listEmailViewModel.selectedItems.contains(index)

                    ListEmail(
                        name = filteredEmailDataList[index].receiveNome ?: "",
                        email = filteredEmailDataList[index].receiveEmail ?: "",
                        subject = filteredEmailDataList[index].subject ?: "",
                        body = filteredEmailDataList[index].body ?: "",
                        time = FormatTime(filteredEmailDataList[index].receivedAt),
                        index = index,
                        isFavorite = isFavorite,
                        onToggleFavorite = { emailIndex -> listEmailViewModel.toggleFavorite(emailIndex) },
                        isSelected = isSelected,
                        onItemSelected = { emailIndex ->
                            listEmailViewModel.toggleItemSelected(
                                emailIndex
                            )
                        },
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
    name: String,
    email: String,
    body: String,
    subject: String,
    time: String,
    index: Int,
    isFavorite: Boolean,
    onToggleFavorite: (Int) -> Unit,
    isSelected: Boolean,
    onItemSelected: (Int) -> Unit,
    valController: NavController
) {

    val context = LocalContext.current

    var favorited by remember { mutableStateOf(false) }
    val customAzulClaro = colorResource(id = R.color.customAzulClaro)

    val colors = MaterialTheme.colorScheme

    val emailPreviewLength = 50
    val previewText = if (body.length > emailPreviewLength) {
        body.take(emailPreviewLength) + "..."
    } else {
        body
    }

    ElevatedCard(
        onClick = {
            valController.navigate("emailDetail/${name}/${email}/${body}/${subject}/${time}")
        },
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
                        Text(text = name, color = if (isSelected) colors.surface else colors.onPrimary, fontSize = 16.sp)
                        Text(text = email, color = if (isSelected) colors.surface else colors.onPrimary)
                    }
                    Column {
                        Text(text = time, color = if (isSelected) colors.surface else colors.onPrimary)
                        IconButton(onClick = {
                            favorited = !favorited
                            onToggleFavorite(index)
                        }) {
                            val iconColor = if (isFavorite) {
                                colorResource(id = R.color.star_cor)
                            } else {
                                LocalContentColor.current
                            }
                            Icon(
                                painter = painterResource(id = if (isFavorite) R.drawable.star else R.drawable.star_outline),
                                contentDescription = if (isFavorite) "Favoritado" else "Não favoritado",
                                tint = iconColor
                            )
                        }
                    }
                }

            }
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 15.dp),
                    text = subject,
                    color = if (isSelected) colors.surface else colors.onPrimary,
                    fontSize = 16.sp
                )
                Text(
                    text = previewText,
                    color = if (isSelected) colors.surface else colors.onPrimary
                )
            }
        }
    }
}

fun vibrate(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    vibrator?.let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            it.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            it.vibrate(50)
        }
    }
}