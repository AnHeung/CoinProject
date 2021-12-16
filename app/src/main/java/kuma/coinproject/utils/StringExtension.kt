package kuma.coinproject.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun String?.orZero() = this ?: "0"

@SuppressLint("SimpleDateFormat")
fun String?.formatDate(): String = try {
    this?.let {
        val date = SimpleDateFormat(DATE_FORMAT).parse(this)
        SimpleDateFormat(DATE_FORMAT).format(date)
    } ?: "2021-01-01"
} catch (e: Exception) {
    "2021-01-01"
}

fun String?.orNoInfomation():String = if(this.isNullOrEmpty()) NO_COIN_INFORMATION else this



