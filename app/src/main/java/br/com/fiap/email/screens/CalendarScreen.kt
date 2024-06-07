package br.com.fiap.email.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var events by remember { mutableStateOf(mapOf<LocalDate, String>()) }
    var showEventDialog by remember { mutableStateOf(false) }
    var eventText by remember { mutableStateOf("") }
    var dialogDate by remember { mutableStateOf<LocalDate?>(null) }

    if (showEventDialog) {
        EventDialog(
            date = dialogDate!!,
            onEventAdd = { date, event ->
                events = events.toMutableMap().apply { put(date, event) }
                showEventDialog = false
            },
            onDismiss = { showEventDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MonthHeader(
            selectedDate = selectedDate,
            onPreviousMonth = { selectedDate = selectedDate.minusMonths(1) },
            onNextMonth = { selectedDate = selectedDate.plusMonths(1) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeekDaysHeader()
        Spacer(modifier = Modifier.height(8.dp))
        CalendarGrid(
            yearMonth = YearMonth.from(selectedDate),
            events = events,
            onDayClick = { date ->
                dialogDate = date
                eventText = events[date] ?: ""
                showEventDialog = true
            }
        )
    }
}

@Composable
fun MonthHeader(selectedDate: LocalDate, onPreviousMonth: () -> Unit, onNextMonth: () -> Unit) {
    val monthName = selectedDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val year = selectedDate.year

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }
        Text(
            text = "$monthName $year",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onNextMonth) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@Composable
fun WeekDaysHeader() {
    val daysOfWeek = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (day in daysOfWeek) {
            Text(
                text = day,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CalendarGrid(yearMonth: YearMonth, events: Map<LocalDate, String>, onDayClick: (LocalDate) -> Unit) {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = lastDayOfMonth.dayOfMonth

    Column {
        var currentDay = 1
        for (week in 0..5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in 0..6) {
                    val day = if (week == 0 && dayOfWeek < firstDayOfWeek) {
                        null
                    } else if (currentDay > daysInMonth) {
                        null
                    } else {
                        currentDay++
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .background(if (day != null) Color.LightGray else Color.Transparent)
                            .clickable(enabled = day != null) {
                                if (day != null) {
                                    onDayClick(LocalDate.of(yearMonth.year, yearMonth.month, day))
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (day != null) {
                            val date = LocalDate.of(yearMonth.year, yearMonth.month, day)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = day.toString())
                                if (events.containsKey(date)) {
                                    Text(
                                        text = "*",
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventDialog(date: LocalDate, onEventAdd: (LocalDate, String) -> Unit, onDismiss: () -> Unit) {
    var eventText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Adicionar Evento em ${date.dayOfMonth}/${date.monthValue}/${date.year}")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = eventText,
                    onValueChange = { eventText = it },
                    label = { Text("Evento") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onEventAdd(date, eventText)
                    }) {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}


