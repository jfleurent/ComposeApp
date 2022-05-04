package com.example.composeapp.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun toISO8601String(localDateTime: LocalDateTime) : String{
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
}