package br.com.fiap.email.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R
import br.com.fiap.email.viewmodel.ThemeViewModel
import br.com.fiap.email.viewmodel.UserViewModel
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ConfigScreen(valController: NavController, userViewModel: UserViewModel, themeViewModel: ThemeViewModel) {

    val languageOptions = listOf("br","en","es")
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

    val fontSize by themeViewModel.fontSize.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val language by themeViewModel.updateLanguage.collectAsState()
    var selectedLanguage by remember { mutableStateOf(language) }
    val context = LocalContext.current

    LaunchedEffect(themeViewModel.updateLanguage.value) {
        changeAppLanguage(themeViewModel.updateLanguage.value, context)
    }

    val userId = userViewModel.userId.observeAsState("")

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
            ) {
                IconButton(
                    onClick = {valController.navigate("homeApp")},
                    modifier = Modifier
                        .offset(x = (-170).dp)
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
                    text = stringResource(id = R.string.settings),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            LazyColumn(modifier = Modifier
                .fillMaxHeight(0.8f)
                .padding(end = 20.dp)){
                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 30.dp, bottom = 15.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.theme),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp)
                        )
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = if (isDarkTheme) stringResource(id = R.string.theme_dark) else stringResource(id = R.string.theme_light), color = colors.onPrimary)
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { isDarkTheme ->
                                    val newTheme = if (isDarkTheme) "dark" else "light"
                                    userViewModel.setUserTheme(newTheme)
                                    themeViewModel.toggleTheme(userId.value)
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = colors.primary,
                                    checkedTrackColor = colors.onPrimary,
                                    uncheckedThumbColor = colors.primary,
                                    uncheckedTrackColor = colors.onSurface
                                ),
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(start = 30.dp, top = 15.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.font_size),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                        Slider(
                            value = fontSize,
                            onValueChange = {
                                newSize ->
                                themeViewModel.setLocalFontSize(newSize)
                            },
                            onValueChangeFinished = {
                                themeViewModel.updateFontSize(userId.value)
                            },
                            valueRange = 12f..24f,
                            steps = 3,
                            colors = SliderDefaults.colors(
                                thumbColor = colors.onPrimary,
                                activeTrackColor = colors.onPrimary
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.font_size, fontSize.toInt()),
                            color = colors.onBackground,
                            fontSize = fontSize.sp,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 30.dp, top = 20.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.change_language),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                        Text(text = "Selected Language: $language", fontSize = 14.sp)
                        languageOptions.forEach { language ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedLanguage = language
                                        themeViewModel.setLocalLanguage(language)
                                        themeViewModel.updateNewLanguage(userId.value)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedLanguage == language,
                                    onClick = {
                                        selectedLanguage = language
                                        themeViewModel.setLocalLanguage(language)
                                        themeViewModel.updateNewLanguage(userId.value)
                                              },
                                    colors = RadioButtonDefaults.colors(selectedColor = colors.onPrimary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = when (language) {
                                        "br" -> "Português"
                                        "en" -> "Inglês"
                                        "es" -> "Espanhol"
                                        else -> "Desconhecido"
                                    },
                                    color = colors.onBackground
                                )
                            }
                        }
                    }
                }
            }
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .padding(start = 30.dp, top = 15.dp)
                    .height(150.dp)
            ) {
                Column{
                    Text(
                        text = stringResource(id = R.string.search_history),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 15.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .shadow(
                                    elevation = 5.dp,
                                    shape = RoundedCornerShape(15.dp),
                                    spotColor = customDarkBlue,
                                    clip = false
                                ),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = customDarkBlue)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.lixeira),
                                contentDescription = "Lixeira",
                                tint = Color.White,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = stringResource(id = R.string.clear_history),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                        Button(
                            onClick = {valController.navigate("inicialScreen")},
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        ){
                            Text(text = stringResource(id = R.string.exit))
                        }
                    }
                }
            }
        }
    }
}

fun changeAppLanguage(newLanguage: String, context: Context) {
    val locale = Locale(newLanguage)
    Locale.setDefault(locale)
    val configuration = context.resources.configuration
    configuration.setLocale(locale)
    context.createConfigurationContext(configuration)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}