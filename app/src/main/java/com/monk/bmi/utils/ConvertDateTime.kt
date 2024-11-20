package com.monk.bmi.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ConvertDateTime {
    private const val DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"
    fun convertTime(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return  dateFormat.format(date)
    }
}