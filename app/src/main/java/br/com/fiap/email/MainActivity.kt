package br.com.fiap.email

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.email.components.EmailModel
import br.com.fiap.email.navigation.AppNavigation
import br.com.fiap.email.navigation.CalendarNavigation
import br.com.fiap.email.navigation.Screens
import br.com.fiap.email.network.ApiClient
import br.com.fiap.email.network.AuthService
import br.com.fiap.email.screens.CalendarScreen
import br.com.fiap.email.screens.ConfigScreen
import br.com.fiap.email.screens.FavoritesScreen
import br.com.fiap.email.screens.HomeScreen
import br.com.fiap.email.screens.InicialScreen
import br.com.fiap.email.screens.LoginScreen
import br.com.fiap.email.screens.PromotionsScreen
import br.com.fiap.email.screens.RegisterScreen
import br.com.fiap.email.screens.ResponseScreen
import br.com.fiap.email.screens.SentScreen
import br.com.fiap.email.screens.WriteScreen
import br.com.fiap.email.ui.theme.EmailTheme
import br.com.fiap.email.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailTheme {
                val userViewModel: UserViewModel = viewModel();
                val authService = ApiClient.authService
                TelaSettings( authService, userViewModel)
            }
        }
    }
}

@Composable
fun TelaSettings(authService: AuthService, userViewModel: UserViewModel) {
    val valController = rememberNavController()
    NavHost(navController = valController, startDestination = "inicialScreen") {
        composable(route = "inicialScreen") {
            InicialScreen(valController)
        }
        composable(route = "login") {
            LoginScreen( userViewModel,valController, authService){
                valController.navigate("homeApp") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable(route = "register") {
            RegisterScreen(valController, authService) {
                valController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            }
        }
        composable(route = "homeApp") {
            AppNavigation(valController, userViewModel)
        }
        composable(route = "settings") {
            ConfigScreen(valController)
        }
        composable(route = "writeEmail?subject={subject}&body={body}") {
            WriteScreen(valController, userViewModel,
                it.arguments?.getString("subject")?: "",
                it.arguments?.getString("body")?: "")

        }
        composable(route = "emailDetail/{name}/{email}/{subject}/{body}") {
            EmailModel(
                valController,
                it.arguments?.getString("name")!!,
                it.arguments?.getString("email")!!,
                it.arguments?.getString("subject")!!,
                it.arguments?.getString("body")!!
            )
        }
        composable(route = "responseEmail/{name}/{email}/{subject}") {
            ResponseScreen(
                valController,
                it.arguments?.getString("name")!!,
                it.arguments?.getString("email")!!,
                it.arguments?.getString("subject")!!,
                userViewModel
            )
        }
        composable(route = "calendar") {
            CalendarNavigation(valController)
        }
        composable(route = "sentScreen"){
            SentScreen(valController, userViewModel)
        }
    }
}



