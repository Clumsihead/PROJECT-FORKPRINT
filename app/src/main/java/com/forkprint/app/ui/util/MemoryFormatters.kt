package com.forkprint.app.ui.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private val DateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy").withZone(ZoneId.systemDefault())
private val TimeFormatter = DateTimeFormatter.ofPattern("h:mm a").withZone(ZoneId.systemDefault())
private val MonthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy").withZone(ZoneId.systemDefault())
private val EditFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

fun formatMemoryDate(value: Instant): String = DateFormatter.format(value)
fun formatMemoryTime(value: Instant): String = TimeFormatter.format(value)
fun formatTimelineMonth(value: Instant): String = MonthFormatter.format(value)
fun formatEditDateTime(value: Instant): String = LocalDateTime.ofInstant(value, ZoneId.systemDefault()).format(EditFormatter)

fun parseEditDateTime(value: String): Instant? = try {
    LocalDateTime.parse(value.trim(), EditFormatter).atZone(ZoneId.systemDefault()).toInstant()
} catch (_: DateTimeParseException) {
    null
}

fun formatDuration(startedAt: Instant, endedAt: Instant?): String {
    val end = endedAt ?: return "Still open"
    val duration = Duration.between(startedAt, end).takeUnless { it.isNegative } ?: return "Unknown duration"
    val hours = duration.toHours()
    val minutes = duration.minusHours(hours).toMinutes()
    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
        hours > 0 -> "${hours}h"
        else -> "${minutes}m"
    }
}

fun sourceLabel(sourceName: String): String = when (sourceName) {
    "Manual" -> "Added by you"
    "Detected" -> "Remembered automatically"
    "Imported" -> "Imported memory"
    else -> sourceName
}
