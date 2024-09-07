package br.com.fiap.email.components

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun FormatTime(dateTimeString: String?): String {
    if (dateTimeString.isNullOrEmpty() || dateTimeString == "0001-01-01T00:00:00Z") {
        return "unknown"
    }

    val formats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )

    var parsedDateTime: LocalDateTime? = null
    for (format in formats) {
        try {
            val formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneOffset.UTC)
            parsedDateTime = LocalDateTime.parse(dateTimeString, formatter)
            break
        } catch (e: DateTimeParseException) {

        }
    }

    if (parsedDateTime == null) {
        return "unknown"
    }

    val now = LocalDateTime.now()
    val duration = Duration.between(parsedDateTime, now)

    return when {
        duration.toMinutes() < 1 -> "just now"
        duration.toMinutes() < 60 -> "${duration.toMinutes()} min ago"
        duration.toHours() < 24 -> "${duration.toHours()} h ago"
        duration.toDays() < 7 -> "${duration.toDays()} d ago"
        else -> {
            val weeks = duration.toDays() / 7
            if (weeks == 1L) "$weeks wk ago" else "$weeks wks ago"
        }
    }
}