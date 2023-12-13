package com.nsicyber.boilerplate.common

import java.text.SimpleDateFormat
import java.util.Locale

fun String.isEmailValid(): Boolean {
    val emailRegex = Regex("^([a-zA-Z0-9_\\.-]+)@([\\da-zA-Z\\.-]+)\\.([a-zA-Z\\.]{2,6})\$")
    return emailRegex.matches(this)
}


fun String?.formatDate(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en"))
    val outputFormat = SimpleDateFormat("dd MMMM yyyy - HH.mm",  Locale("en"))

    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}