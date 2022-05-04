package com.example.composeapp.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.ellipsis(limit: Int): String {
    return run {
        if (length >= limit) {
            substring(0, limit).plus("...")
        } else {
            this
        }
    }
}

fun String.toLocalDataTime(): LocalDateTime = LocalDateTime.parse(
    this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .withZone(ZoneId.of("UTC"))
)
