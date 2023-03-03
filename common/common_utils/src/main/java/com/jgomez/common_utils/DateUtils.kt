package com.jgomez.common_utils

import java.text.SimpleDateFormat
import java.util.Locale


class DateUtils {

    fun dateToStringFormat(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)

        if(dateFormat != null) {
            val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(dateFormat)
            val day = SimpleDateFormat("dd", Locale.getDefault()).format(dateFormat)
            return day + " " +monthName.replaceFirstChar(Char::titlecase)
        }

        return date
    }
}