package br.com.fiap.email.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.email.R

@Composable
fun ButtonWrite(valController: NavController) {

    val azul_escuro: Color = colorResource(id = R.color.azul_escuro)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            onClick = {valController.navigate("writeEmail")},
            modifier = Modifier
                .background(azul_escuro, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "escrever novo email",
                modifier = Modifier.size(28.dp),
                tint = Color.White
            )
        }
    }
}