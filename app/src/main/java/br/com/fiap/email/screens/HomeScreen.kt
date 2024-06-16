package br.com.fiap.email.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import br.com.fiap.email.R
import br.com.fiap.email.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(valController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart,
    ) {
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .padding(top = 80.dp)
                .height(1.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(top = 90.dp)
                .padding(horizontal = 25.dp)
        ) {
            Text(
                text = "Default",
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 15.dp, bottom = 15.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Card(
                onClick = { valController.navigate("calendar") },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendario",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Text(
                        text = "Calendario",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
            Card(
                onClick = { valController.navigate(Screens.FavoritesScreen.name) },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.starblack),
                        contentDescription = "Favoritos",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Text(
                        text = "Favoritos",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
            Card(
                onClick = { /*TODO*/ },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "Ler Depois",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Text(
                        text = "Ler Depois",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
            Card(
                onClick = { /*TODO*/ },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "Enviados",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Text(
                        text = "Enviados",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
            Card(
                onClick = { /*TODO*/ },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.spam),
                        contentDescription = "Spam",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Text(
                        text = "Spam",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
            Card(
                onClick = { /*TODO*/ },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Lixeira",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Text(
                        text = "Lixeira",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black,
                        fontSize = 16.sp
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