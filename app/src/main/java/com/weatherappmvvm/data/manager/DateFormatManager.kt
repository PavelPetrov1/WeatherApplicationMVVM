package com.weatherappmvvm.data.manager

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatManager {

    const val DEFAULT_WEATHER_DATA_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val YEAR_MONTH_DAY_HOUR_MINUTES_FORMAT = "yyyy-MM-dd/HH:mm:ss"

    fun String.formatDateTo(currentFormat: String, targetFormat: String): String? {
        val originalDateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
        val targetDateFormat = SimpleDateFormat(targetFormat, Locale.getDefault())
        val date = originalDateFormat.parse(this)

        // If parsing is successful, return the formatted date else return the original string
        return if (date != null) {
            targetDateFormat.format(date)
        } else {
            this
        }
    }
}
