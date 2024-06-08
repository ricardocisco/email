package br.com.fiap.email.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.fiap.email.R
import br.com.fiap.email.components.ButtonWrite
import br.com.fiap.email.screens.CalendarGantScreen
import br.com.fiap.email.screens.CalendarScreen
import br.com.fiap.email.screens.CalendarTaskScreen
import br.com.fiap.email.screens.FavoritesScreen
import br.com.fiap.email.screens.HomeScreen
import br.com.fiap.email.screens.PromotionsScreen
import br.com.fiap.email.screens.ShowEventDialog
import br.com.fiap.email.viewmodel.ListEmailViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarNavigation() {
    val calendarController = rememberNavController()
    val listEmailViewModel = remember { ListEmailViewModel() }
    var showEventDialog by remember { mutableStateOf(false) }
    var events by remember { mutableStateOf(mapOf<LocalDate, String>()) }

    if (showEventDialog) {
        ShowEventDialog(
            onEventAdd = { date, event ->
                events = events.toMutableMap().apply { put(date, event) }
                showEventDialog = false
            },
            onDismiss = { showEventDialog = false }
        )
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Botão de Voltar",
                        tint = Color.Black,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
                IconButton(onClick = {
                    showEventDialog = true
                }) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.addevent),
                        contentDescription = "Adicionar Evento"
                    )
                }
            }
        }
        Box(
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.White,
                    ) {
                        val navBackStackEntry by calendarController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        listCalendarNavItems.forEach { calendarNav ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == calendarNav.route } == true,
                                onClick = {
                                    calendarController.navigate(calendarNav.route) {
                                        popUpTo(calendarController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Image(
                                        painter = painterResource(id = calendarNav.icon),
                                        contentDescription = calendarNav.label,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            ) {
                NavHost(
                    navController = calendarController,
                    startDestination = ScreenCalendar.CalendarScreen.name,
                ) {
                    composable(route = ScreenCalendar.CalendarScreen.name) {
                        CalendarScreen(events = events, onEventAdd = { date, event ->
                            events = events.toMutableMap().apply { put(date, event) }
                        })
                    }
                    composable(route = ScreenCalendar.CalendarTaskScreen.name) {
                        CalendarTaskScreen()
                    }
                    composable(route = ScreenCalendar.CalendarGantScreen.name) {
                        CalendarGantScreen()
                    }
                }
            }
        }
    }
}


