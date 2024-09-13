package br.com.fiap.email.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.email.R
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(events: Map<LocalDate, MutableList<String>>, onEventAdd: (LocalDate, String) -> Unit) {
    val colors = MaterialTheme.colorScheme

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showEventDialog by remember { mutableStateOf(false) }
    var dialogDate by remember { mutableStateOf<LocalDate?>(null) }

    if (showEventDialog) {
        EventDialog(
            date = dialogDate ?: LocalDate.now(),
            onEventAdd = { date, event ->
                onEventAdd(date, event)
                showEventDialog = false
            },
            onDismiss = { showEventDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth().background(Color.White)
                .background(colors.background)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 6.dp)
            ){
                MonthHeader(
                    selectedDate = selectedDate,
                    onPreviousMonth = { selectedDate = selectedDate.minusMonths(1) },
                    onNextMonth = { selectedDate = selectedDate.plusMonths(1) },
                    onMonthSelected = { selectedDate = selectedDate.withMonth(it) },
                    onYearSelected = { selectedDate = selectedDate.withYear(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeekDaysHeader()
                Spacer(modifier = Modifier.height(8.dp))
                CalendarGrid(
                    yearMonth = YearMonth.from(selectedDate),
                    events = events,
                    onDayClick = { date ->
                        dialogDate = date
                        showEventDialog = true
                    }
                )
            }
        }
        Divider (
            color = Color.LightGray,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        EventList(events = events)
    }
}

@Composable
fun MonthHeader(
    selectedDate: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onMonthSelected: (Int) -> Unit,
    onYearSelected: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }
    val monthName = selectedDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val year = selectedDate.year
    val months = (1..12).map { it to it.getMonthName() }
    val years = (2000..2100).toList()

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month", tint = colors.onPrimary)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box {
                Text(
                    fontSize = 20.sp,
                    text = monthName,
                    modifier = Modifier
                        .clickable { monthExpanded = true },
                    color = colors.onPrimary
                )
                DropdownMenu(
                    expanded = monthExpanded,
                    onDismissRequest = { monthExpanded = false }
                ) {
                    months.forEach { (month, name) ->
                        DropdownMenuItem(onClick = {
                            onMonthSelected(month)
                            monthExpanded = false
                        }, text = {
                            Text(text = name)
                        }
                        )
                    }
                }
            }
            Text(
                text = ",", color = colors.onPrimary,
            )
            Box {
                Text(
                    fontSize = 20.sp,
                    text = year.toString(),
                    modifier = Modifier
                        .clickable { yearExpanded = true }
                        .padding(8.dp),
                    color = colors.onPrimary
                )
                DropdownMenu(
                    expanded = yearExpanded,
                    onDismissRequest = { yearExpanded = false }
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(onClick = {
                            onYearSelected(year)
                            yearExpanded = false
                        }, text = {
                            Text(text = year.toString())
                        }
                        )
                    }
                }
            }
        }
        IconButton(onClick = onNextMonth) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month", tint = colors.onPrimary)
        }
    }
}

@Composable
fun WeekDaysHeader() {
    val colors = MaterialTheme.colorScheme
    val daysOfWeek = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")
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
                textAlign = TextAlign.Center,
                color = colors.onPrimary
            )
        }
    }
}

@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    events: Map<LocalDate, MutableList<String>>,
    onDayClick: (LocalDate) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = lastDayOfMonth.dayOfMonth

    val customDarkBlue: Color = colorResource(id = R.color.customDarkBlue)

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

                    val date = if (day != null) LocalDate.of(
                        yearMonth.year,
                        yearMonth.month,
                        day
                    ) else null
                    val isEventDay = date != null && events.containsKey(date)
                    val backgroundColor = when {
                        isEventDay -> customDarkBlue
                        else -> Color.Transparent
                    }
                    val textColor = if (isEventDay) colors.primary else colors.onPrimary

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(backgroundColor)
                            .clickable(enabled = day != null) {
                                if (day != null) {
                                    onDayClick(LocalDate.of(yearMonth.year, yearMonth.month, day))
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (day != null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = day.toString(), color = textColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventDialog(
    date: LocalDate,
    onEventAdd: (LocalDate, String) -> Unit,
    onDismiss: () -> Unit
) {
    var eventText by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

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
                BasicTextField(
                    value = eventText,
                    onValueChange = { eventText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.azul_escuro)),
                        onClick = {
                            onEventAdd(date, eventText)
                        }) {
                        Text("Salvar", color = colors.onPrimary)
                    }
                }
            }
        }
    }
}

fun Int.getMonthName(): String {
    return Month.of(this).getDisplayName(TextStyle.FULL, Locale.getDefault())
}

@Composable
fun EventList(events: Map<LocalDate, MutableList<String>>) {
    val colors = MaterialTheme.colorScheme
    LazyColumn(
        modifier = Modifier.padding(bottom = 80.dp).background(colors.background).fillMaxSize()
    ){
        events.forEach { (date, eventList) ->
            item {
                EventItem(date, eventList)
            }
        }
    }
}

@Composable
fun EventItem(date: LocalDate, eventList: List<String>) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(colors.background)
            .padding(8.dp)
    ) {
        Text(
            text = formatDate(date),
            fontWeight = FontWeight.Bold,
            color = colors.onPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        eventList.forEach { event ->
            Text(
                text = event,
                modifier = Modifier.padding(start = 8.dp),
                color = colors.onPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
@Composable
fun ShowEventDialog(
    onEventAdd: (LocalDate, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var eventText by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(id = R.drawable.calendarblue),
                            contentDescription = "Calendar"
                        )
                    }
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(id = R.drawable.editevent),
                            contentDescription = "editar evento"
                        )
                    }
                }
                Text(
                    fontSize = 20.sp,
                    text = "Novo Evento",
                    color = colorResource(id = R.color.azul_escuro),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                    )
                Spacer(modifier = Modifier.height(20.dp))
                DatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                BasicTextField(
                    value = eventText,
                    onValueChange = { eventText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(18.dp),
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(color = colors.onPrimary)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = colors.onPrimary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.azul_escuro)),
                        onClick = {
                        onEventAdd(selectedDate, eventText)
                    }) {
                        Text("Salvar", color = colors.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun DatePicker(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var year by remember { mutableStateOf(selectedDate.year) }
    var month by remember { mutableStateOf(selectedDate.monthValue) }
    var day by remember { mutableStateOf(selectedDate.dayOfMonth) }


    Column(
        modifier = Modifier.background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Ano:")
                Spacer(modifier = Modifier.width(2.dp))
                Box {
                    val expandedYear = remember { mutableStateOf(false) }
                    Text(
                        text = year.toString(),
                        modifier = Modifier
                            .clickable { expandedYear.value = true }
                            .padding(8.dp)
                            .background(Color.Gray.copy(alpha = 0.1f))
                    )
                    DropdownMenu(
                        expanded = expandedYear.value,
                        onDismissRequest = { expandedYear.value = false }
                    ) {
                        (1900..2100).forEach {
                            DropdownMenuItem(onClick = {
                                year = it
                                expandedYear.value = false
                                onDateSelected(LocalDate.of(year, month, day))
                            }, text = {Text(text = it.toString())})
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Mês:")
                Spacer(modifier = Modifier.width(2.dp))
                Box {
                    val expandedMonth = remember { mutableStateOf(false) }
                    Text(
                        text = month.toString(),
                        modifier = Modifier
                            .clickable { expandedMonth.value = true }
                            .padding(8.dp)
                            .background(Color.Gray.copy(alpha = 0.1f))
                    )
                    DropdownMenu(
                        expanded = expandedMonth.value,
                        onDismissRequest = { expandedMonth.value = false }
                    ) {
                        (1..12).forEach {
                            DropdownMenuItem(onClick = {
                                month = it
                                expandedMonth.value = false
                                onDateSelected(LocalDate.of(year, month, day))
                            }, text = {Text(text = it.toString())})
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Dia:")
                Spacer(modifier = Modifier.width(2.dp))
                Box {
                    val expandedDay = remember { mutableStateOf(false) }
                    Text(
                        text = day.toString(),
                        modifier = Modifier
                            .clickable { expandedDay.value = true }
                            .padding(8.dp)
                            .background(Color.Gray.copy(alpha = 0.1f))
                    )
                    DropdownMenu(
                        expanded = expandedDay.value,
                        onDismissRequest = { expandedDay.value = false }
                    ) {
                        (1..31).forEach {
                            DropdownMenuItem(onClick = {
                                day = it
                                expandedDay.value = false
                                onDateSelected(LocalDate.of(year, month, day))
                            }, text = {Text(text = it.toString())})
                        }
                    }
                }
            }
        }
    }
}

