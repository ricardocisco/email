package br.com.fiap.email.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults.containerColor
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.fiap.email.R
import br.com.fiap.email.components.BottomSheetButton
import br.com.fiap.email.components.ButtonWrite
import br.com.fiap.email.screens.FavoritesScreen
import br.com.fiap.email.screens.HomeScreen
import br.com.fiap.email.screens.PromotionsScreen
import br.com.fiap.email.ui.theme.DarkColorScheme
import br.com.fiap.email.ui.theme.LightColorScheme
import br.com.fiap.email.viewmodel.ListEmailViewModel
import br.com.fiap.email.viewmodel.NetworkViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(valController: NavController) {
    val listEmailViewModel = remember { ListEmailViewModel() }
    val isInEditMode by listEmailViewModel.isInEditMode
    var showBottomSheet by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val navController = rememberNavController()

    val colors = MaterialTheme.colorScheme

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colors.surface)
        ) {
            if (isInEditMode) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
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
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.folder),
                                contentDescription = "botao de pastas",
                                tint = colors.onBackground
                            )
                        }
                        IconButton(onClick = { showBottomSheet = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.more),
                                contentDescription = "botao de mais",
                                tint = colors.onBackground
                            )
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isSearching) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            placeholder = { Text("Digite para pesquisar...") },
                            singleLine = true,
                            shape = RoundedCornerShape(50),
                            trailingIcon = {
                                IconButton(onClick = { isSearching = false }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cancelar",
                                        tint = colors.onBackground
                                    )
                                }
                            }
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(7.dp))
                        Image(
                            modifier = Modifier
                                .width(50.dp)
                                .clip(shape = CircleShape),
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Perfil"
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            text = "Rachel Jacobs",
                            color = colors.onBackground,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Default.Search,
                                contentDescription = "Pesquisa",
                                tint = colors.onBackground
                            )
                        }
                        IconButton(onClick = { valController.navigate("settings") }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Configurações",
                                tint = colors.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        NetworkStatus()
                        Spacer(modifier = Modifier.padding(7.dp))
                    }
                }
            }
        }
        Box() {
            Scaffold(
                topBar = {
                    NavigationBar(
                        containerColor = colors.surface,
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        listNavItems.forEach { navItem ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                                onClick = {
                                    navController.navigate(navItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = false
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    BadgedBox(badge = {
                                        if (navItem.badgeCount != null) {
                                            Badge {
                                                Text(text = navItem.badgeCount.toString())
                                            }
                                        } else if (navItem.hasNews) {
                                            Badge()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = navItem.icon,
                                            contentDescription = navItem.label,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                    ButtonWrite(valController)
                    BottomSheetButtonEdit(showBottomSheet) {
                        showBottomSheet = it
                    }
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.HomeScreen.name,
                ) {
                    composable(route = Screens.HomeScreen.name) {
                        HomeScreen(valController, navController)
                    }
                    composable(route = Screens.PromotionsScreen.name) {
                        PromotionsScreen(
                            listEmailViewModel, valController, searchText)
                    }
                    composable(route = Screens.FavoritesScreen.name) {
                        FavoritesScreen(listEmailViewModel, valController, searchText)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetButtonEdit(showBottomSheet: Boolean, onButtonClick: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val azul_escuro: Color = colorResource(id = R.color.azul_escuro)

    val colors = MaterialTheme.colorScheme

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
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.forward),
                            contentDescription = "forward"
                        )
                        Text(
                            text = "Encaminhar",
                            modifier = Modifier.padding(start = 10.dp),
                            color = colors.onPrimary
                        )
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
                        Image(
                            painter = painterResource(id = R.drawable.folderimg),
                            contentDescription = "pastas"
                        )
                        Text(
                            text = "Adicionar a Pasta",
                            modifier = Modifier.padding(start = 10.dp),
                            color = colors.onPrimary
                        )
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
                        Image(
                            painter = painterResource(id = R.drawable.spam),
                            contentDescription = "spam"
                        )
                        Text(
                            text = "Denunciar Spam",
                            modifier = Modifier.padding(start = 10.dp),
                            color = colors.onPrimary
                        )
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
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "delete"
                        )
                        Text(
                            text = "Deletar",
                            modifier = Modifier.padding(start = 10.dp),
                            color = azul_escuro
                        )
                    }
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


@Composable
fun NetworkStatus(viewModel: NetworkViewModel = viewModel()) {
    val isConnected = viewModel.connectivityLiveData.observeAsState(initial = false)

    if (isConnected.value == true) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.interneton),
            contentDescription = "Online"
        )
    } else {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.internetoff),
            contentDescription = "Offline"
        )
    }
}







