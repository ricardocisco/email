package br.com.fiap.email.navigation

import br.com.fiap.email.R

data class CalendarNavItem(
    val label: String,
    val icon: Int,
    val route: String,
)

val listCalendarNavItems = listOf(
    CalendarNavItem(
        label = "Calendar",
        icon = R.drawable.calendar,
        route = ScreenCalendar.CalendarScreen.name
    ),
    CalendarNavItem(
        label = "Task",
        icon = R.drawable.task,
        route = ScreenCalendar.CalendarTaskScreen.name
    ),
    CalendarNavItem(
        label = "Gant",
        icon = R.drawable.gant,
        route = ScreenCalendar.CalendarGantScreen.name
    ),
)