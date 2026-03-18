package com.example.collegeschedulepavlenkomobil.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun getWeekDateRange(): Pair<String, String> {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ISO_DATE
    var start = if (today.dayOfWeek == DayOfWeek.SUNDAY) today.plusDays(1) else today
    var daysAdded = 0
    var end = start
    while (daysAdded < 5) {
        end = end.plusDays(1)
        if (end.dayOfWeek != DayOfWeek.SUNDAY) {
            daysAdded++
        }
    }
    return start.format(formatter) to end.format(formatter)
}

fun formatHumanReadableDate(dateString: String): String {
    return try {

        val dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
        val date = dateTime.toLocalDate()
        val day = date.dayOfMonth
        val month = date.month.getDisplayName(TextStyle.FULL, Locale("ru"))
        val year = date.year
        "$day $month $year"
    } catch (e: Exception) {

        try {
            val date = LocalDate.parse(dateString)
            val day = date.dayOfMonth
            val month = date.month.getDisplayName(TextStyle.FULL, Locale("ru"))
            val year = date.year
            "$day $month $year"
        } catch (e2: Exception) {

            dateString
        }
    }
}