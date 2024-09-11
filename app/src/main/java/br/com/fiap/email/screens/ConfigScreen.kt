package br.com.fiap.email.screens

import android.widget.Switch
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(valController: NavController, userViewModel: UserViewModel, themeViewModel: ThemeViewModel) {

    var selectedSorting by remember { mutableStateOf("Mais Recentes") }
    var selectedLanguage by remember { mutableStateOf("Português") }

    val fontSizeOptions = listOf(12f, 16f, 20f, 24f)
    val sortingOptions = listOf(stringResource(id = R.string.most_recent), stringResource(id = R.string.oldest), stringResource(id = R.string.by_subject))
    val languageOptions = listOf(stringResource(id = R.string.pt), stringResource(id = R.string.en), stringResource(id = R.string.es))
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

    val fontSize by themeViewModel.fontSize.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
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
                            text = stringResource(id = R.string.sort_emails),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                        sortingOptions.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedSorting = option },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedSorting == option,
                                    onClick = { selectedSorting = option },
                                    colors = RadioButtonDefaults.colors(selectedColor = colors.onPrimary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = option, color = colors.onBackground)
                            }
                        }
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
                        languageOptions.forEach { language ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedLanguage = language },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedLanguage == language,
                                    onClick = { selectedLanguage = language },
                                    colors = RadioButtonDefaults.colors(selectedColor = colors.onPrimary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = language, color = colors.onBackground)
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