package br.com.fiap.email.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.email.R


@Composable
fun EmailModel(valController: NavController, name: String, email: String) {

    val azul_escuro: Color = colorResource(id = R.color.azul_escuro)
    val gray_100: Color = colorResource(id = R.color.gray_100)

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .shadow(1.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = {valController.navigate("homeApp")}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "botao de voltar")
            }
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.folder),
                        contentDescription = "botao de pastas"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = "botao de mais"
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    modifier = Modifier
                        .width(70.dp)
                        .clip(shape = CircleShape),
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Perfil"
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column {
                        Text(text = "$name", fontSize = 18.sp)
                        Text(text = "$email", fontSize = 12.sp)
                    }
                    Text(text = "1h ago")
                }
            }
            Column{
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "What is your opinion about this boss?", fontSize = 18.sp)
                }
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                                "Sit amet consectetur adipiscing elit duis tristique. Enim lobortis scelerisque fermentum dui faucibus in ornare. Faucibus vitae aliquet nec ullamcorper sit amet risus nullam. " +
                                "Risus feugiat in ante metus dictum. Iaculis at erat pellentesque adipiscing commodo elit at imperdiet. Adipiscing tristique risus nec feugiat in. Nibh praesent tristique magna sit amet purus gravida. " +
                                "Egestas diam in arcu cursus euismod quis viverra nibh cras. Tincidunt nunc pulvinar sapien et ligula ullamcorper. Rhoncus mattis rhoncus urna neque viverra justo nec. Quisque non tellus orci ac. " +
                                "Cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris. " +
                                "Sed tempus urna et pharetra.", fontSize = 14.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .background(gray_100, RoundedCornerShape(8.dp))
                        .padding(30.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.forward),
                        contentDescription = "forward",
                    )
                    Text(text = "Encaminhar", modifier = Modifier.padding(start = 10.dp))
                }
                Row(
                    modifier = Modifier
                        .background(azul_escuro, RoundedCornerShape(8.dp))
                        .padding(40.dp, 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.reply),
                        contentDescription = "forward"
                    )
                    Text(text = "Responder", color = Color.White, modifier = Modifier.padding(start = 10.dp))
                }
            }
        }
    }
}