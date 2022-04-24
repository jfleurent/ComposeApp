package com.example.composeapp.util

fun String.ellipsis(limit : Int) : String {
    return run {
        if (length >= limit) {
            substring(0, limit).plus("...")
        } else {
            this
        }
    }
}