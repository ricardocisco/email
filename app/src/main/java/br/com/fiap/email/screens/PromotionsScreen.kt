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
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import br.com.fiap.email.viewmodel.ListEmailViewModel
import io.github.serpro69.kfaker.Faker

@Composable
fun PromotionsScreen(
    listEmailViewModel: ListEmailViewModel,
    valController: NavController,
    searchText: String,
) {

    val emailDataList = rememberEmailDataList()
    val filteredEmailDataList = emailDataList.filter {
        it.name.contains(searchText, ignoreCase = true) || it.email.contains(
            searchText,
            ignoreCase = true
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .padding(top = 80.dp)
                .height(1.dp)
                .fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier.padding(top = 85.dp)
        ) {
            items(filteredEmailDataList.size) { index ->
                val isFavorite = listEmailViewModel.isFavorite(index)
                val isSelected = listEmailViewModel.selectedItems.contains(index)
                ListEmail(
                    name = emailDataList[index].name,
                    email = emailDataList[index].email,
                    index = index,
                    isFavorite = isFavorite,
                    onToggleFavorite = { emailIndex -> listEmailViewModel.toggleFavorite(emailIndex) },
                    isSelected = isSelected,
                    onItemSelected = { emailIndex ->
                        listEmailViewModel.toggleItemSelected(
                            emailIndex
                        )
                    },
                    valController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListEmail(
    name: String,
    email: String,
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

    ElevatedCard(
        onClick = {
            valController.navigate("emailDetail/${name}/${email}")
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
                    onClick = { valController.navigate("emailDetail/${name}/${email}") },
                    onLongClick = {
                        vibrate(context)
                        onItemSelected(index)
                    }
                )
                .background(if (isSelected) customAzulClaro else Color.White)
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
                        Text(text = name, color = Color.Black, fontSize = 16.sp)
                        Text(text = "International Officer", color = Color.Black)
                        Text(text = email, color = Color.Black)
                    }
                    Column {
                        Text(text = "12m ago", color = Color.Black)
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
                    text = "Lorem ipsum dolor sit amet",
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Sit amet consectetur adipiscing elit duis tristique. Enim lobortis scelerisque fermentum dui faucibus in ornare. ",
                    color = Color.Black
                )
            }
//            Divider(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                color = Color.LightGray,
//                thickness = 1.dp
//            )
        }
    }
}

@Composable
private fun rememberEmailDataList(): List<EmailData> {
    val emailDataList = remember {
        (0 until 10).map { index ->
            EmailData(generateNameWithFaker(), generateEmailWithFaker())
        }
    }
    return emailDataList
}

data class EmailData(
    val name: String,
    val email: String
)

fun generateNameWithFaker(): String {
    val faker = Faker()
    return faker.name.name()
}

fun generateEmailWithFaker(): String {
    val faker = Faker()
    return faker.internet.email()
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