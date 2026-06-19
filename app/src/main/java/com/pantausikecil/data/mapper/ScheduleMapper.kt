package com.pantausikecil.data.mapper

import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.network.ScheduleDto
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun ScheduleDto.toUiSchedule(): PosyanduSchedule {
    val parsedDateTime = formatScheduleDateTime(scheduledAt)

    return PosyanduSchedule(
        id = jadwalId,
        title = judul,
        date = parsedDateTime.first,
        time = parsedDateTime.second,
        activity = kegiatan ?: "-"
    )
}

private fun formatScheduleDateTime(value: String): Pair<String, String> {
    return try {
        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(value)

        val dateFormat = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        val timeFormat = SimpleDateFormat(
            "HH:mm",
            Locale("id", "ID")
        )

        if (date != null) {
            dateFormat.format(date) to timeFormat.format(date)
        } else {
            "-" to "-"
        }
    } catch (e: Exception) {
        "-" to "-"
    }
}