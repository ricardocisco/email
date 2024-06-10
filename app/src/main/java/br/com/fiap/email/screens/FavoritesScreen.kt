package br.com.fiap.email.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun FavoritesScreen(listEmailViewModel: ListEmailViewModel, valController: NavController) {

    val emailDataList = rememberEmailDataList()
    val selectedItems = remember { mutableStateListOf<Int>() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 80.dp)
        ) {
            val favoriteEmails = listEmailViewModel.favoriteEmails.toList()
            items(favoriteEmails) { index ->
                val isSelected = selectedItems.contains(index)
                ListEmail(
                    name = emailDataList[index].name,
                    email = emailDataList[index].email,
                    index = index,
                    isFavorite = true,
                    onToggleFavorite = { emailIndex ->
                        listEmailViewModel.toggleFavorite(emailIndex)
                    },
                    isSelected = isSelected,
                    onItemSelected = { emailIndex ->
                        if (selectedItems.contains(emailIndex)) {
                            selectedItems.remove(emailIndex)
                        } else {
                            selectedItems.add(emailIndex)
                        }
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
