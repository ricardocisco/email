package br.com.fiap.email.navigation

import br.com.fiap.email.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val listNavItems = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name,
        hasNews = false
    ),
    NavItem(
        label = "Promotions",
        icon = Icons.Default.Email,
        route = Screens.PromotionsScreen.name,
        hasNews = false,
    ),
    NavItem(
        label = "Favorite",
        icon = Icons.Default.Star,
        route = Screens.FavoritesScreen.name,
        hasNews = false,
    ),
)