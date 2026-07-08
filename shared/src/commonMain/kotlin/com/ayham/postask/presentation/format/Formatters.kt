package com.ayham.postask.presentation.format

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun Long.toMoneyLabel(): String {
    val negative = this < 0
    val absolute = if (negative) -this else this
    val whole = absolute / 100
    val fraction = absolute % 100
    val fractionLabel = if (fraction < 10) "0$fraction" else "$fraction"
    val sign = if (negative) "-" else ""
    return "$sign$$whole.$fractionLabel"
}

fun Long.toDateTimeLabel(): String {
    val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = (dateTime.month.ordinal + 1).pad2()
    val day = dateTime.day.pad2()
    val hour = dateTime.hour.pad2()
    val minute = dateTime.minute.pad2()
    return "${dateTime.year}-$month-$day $hour:$minute"
}

private fun Int.pad2(): String = if (this < 10) "0$this" else "$this"
