package br.com.fiap.email.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import br.com.fiap.email.viewmodel.ListEmailViewModel
import io.github.serpro69.kfaker.Faker

@Composable
fun FavoritesScreen(
    listEmailViewModel: ListEmailViewModel,
    valController: NavController,
    searchText: String,
) {
    val emailDataList = rememberEmailDataList()
    val filteredFavoriteEmails = listEmailViewModel.favoriteEmails
        .filter {
            emailDataList[it].name.contains(
                searchText,
                ignoreCase = true
            ) || emailDataList[it].email.contains(searchText, ignoreCase = true)
        }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.TopStart
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
            items(filteredFavoriteEmails) { index ->
                val isSelected = listEmailViewModel.selectedItems.contains(index)
                val emailData = emailDataList[index]
                ListEmail(
                    name = emailData.name,
                    email = emailData.email,
                    index = index,
                    isFavorite = true,
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

@Composable
private fun rememberEmailDataList(): List<EmailData> {
    val emailDataList = remember {
        (0 until 10).map { index ->
            EmailData(generateNameWithFaker(), generateEmailWithFaker())
        }
    }
    return emailDataList
}
