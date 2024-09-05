package br.com.fiap.email.components

import android.app.Dialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogLoading(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    message: String,
    isLoading: Boolean,
    delayTime: Long = 9000
){
    if (showDialog) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(delayTime)
            onDismiss()
        }

        Dialog(onDismissRequest = { onDismiss() }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(100.dp)
                    .width(250.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                if(isLoading){
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}