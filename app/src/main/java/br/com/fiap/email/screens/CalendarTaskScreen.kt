package br.com.fiap.email.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun CalendarTaskScreen(events: Map<LocalDate, MutableList<String>>, onEventAdd: (LocalDate, String) -> Unit){
    var showEventDialog by remember { mutableStateOf(false) }
    val selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val colors = MaterialTheme.colorScheme

    if (showEventDialog) {
        EventDialog(
            date = selectedDate,
            onEventAdd = { date, event ->
                onEventAdd(date, event)
                showEventDialog = false
            },
            onDismiss = { showEventDialog = false }
        )
    }

    Column(
        modifier = Modifier.background(color = colors.background).fillMaxSize()
    ){
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart

        ){
            Row(modifier = Modifier.padding(16.dp)){
                Text(text = "Tarefas do dia", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = colors.onPrimary)
            }
        }
        Box(modifier = Modifier.padding(horizontal = 16.dp)){
        TaskList(events = events)
            Spacer(modifier = Modifier.padding(bottom = 80.dp))
        }
    }    
}


@Composable
fun TaskList(events: Map<LocalDate, MutableList<String>>) {
    var state by remember { mutableStateOf(true) }
    val colors = MaterialTheme.colorScheme
    LazyColumn {
        events.forEach { (date, eventList) ->
            item {
                Column(modifier = Modifier){
                    Text(
                        text = formatDate(date),
                        fontSize = 22.sp,
                        color = colors.onPrimary
                    )
                    eventList.forEach { event ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = state, onCheckedChange = { state = !state })
                            Text(
                                text = event,
                                fontSize = 20.sp,
                                color = colors.onPrimary
                            )
                        }
                    }
                }
                Divider (
                    color = colors.onPrimary,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("pt", "BR"))
    return date.format(formatter)
}

