package br.com.fiap.email.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.email.R

@Composable
fun CalendarGantScreen(){

    val customAzulClaro: Color = colorResource(id = R.color.customAzulClaro)
    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

    val vermelhoBg: Color = colorResource(id = R.color.vermelhoBg)
    val vermelhoText: Color = colorResource(id = R.color.vermelhoText)

    Row(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .drawBehind {
                    val borderSize = 1.dp.toPx()
                    val x = size.width - borderSize / 1
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = borderSize
                    )
                }
                .fillMaxHeight()
                .width(80.dp)
                .background(Color.Gray.copy(alpha = 0.1f)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "21")
                Text(text = "SÁB", color = Color.Gray)
                Divider (
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "22")
                Text(text = "SEG", color = Color.Gray)
                Divider (
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "23")
                Text(text = "TER", color = Color.Gray)
                Divider (
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "24")
                Text(text = "QUA", color = Color.Gray)
                Divider (
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "25")
                Text(text = "QUI", color = Color.Gray)
                Divider (
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "26")
                Text(text = "SEX", color = Color.Gray)
                Divider (
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .drawBehind {
                        val borderSize = 1.dp.toPx()
                        val y = size.height - borderSize / 1
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = borderSize
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Column(modifier = Modifier){
                    Text(text = "16h00")
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier){
                    Text(text = "17h00")
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier){
                    Text(text = "18h00")
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier){
                    Text(text = "19h00")
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
            ){
                Row(modifier = Modifier
                    .background(customAzulClaro)
                    .fillMaxWidth()
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Aniversario", color = customDarkBlue)
                }
                Row(modifier = Modifier
                    .padding(top = 20.dp)
                    .background(vermelhoBg)
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Reuniao", color = vermelhoText)
                }
                Row(modifier = Modifier
                    .background(customAzulClaro)
                    .fillMaxWidth()
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Reuniao", color = customDarkBlue)
                }
                Row(modifier = Modifier
                    .padding(top = 20.dp)
                    .background(vermelhoBg)
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Almoco Familia", color = vermelhoText)
                }
                Row(modifier = Modifier
                    .background(customAzulClaro)
                    .fillMaxWidth()
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Galeria de Arte", color = customDarkBlue)
                }
                Row(modifier = Modifier
                    .padding(top = 20.dp)
                    .background(vermelhoBg)
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Aula de Violao", color = vermelhoText)
                }
                Row(modifier = Modifier
                    .background(customAzulClaro)
                    .fillMaxWidth()
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Ingles", color = customDarkBlue)
                }
                Row(modifier = Modifier
                    .padding(top = 20.dp)
                    .background(vermelhoBg)
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Compras", color = vermelhoText)
                }
                Row(modifier = Modifier
                    .background(customAzulClaro)
                    .fillMaxWidth()
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Banho Pet", color = customDarkBlue)
                }
                Row(modifier = Modifier
                    .padding(top = 20.dp)
                    .background(vermelhoBg)
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Reuniao", color = vermelhoText)
                }
            }
        }
    }
}