package kuma.coinproject.utils

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun String?.orZero() = this ?: "0"

@SuppressLint("SimpleDateFormat")
fun String?.formatDate(): String = try {
    this?.let {
        val inputFormatter = DateTimeFormatter.ofPattern(BASE_DATE_FORMAT, Locale.KOREA)
        val outputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT,Locale.KOREA)
        val date = LocalDate.parse(this, inputFormatter)
        outputFormatter.format(date)
    } ?: NO_COIN_DATE
} catch (e: Exception) {
    NO_COIN_DATE
}

fun String?.orNoInformation():String = if(this.isNullOrEmpty()) NO_COIN_INFORMATION else this



