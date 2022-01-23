package ru.mrroot.qr_code_db.utils

import androidx.room.TypeConverter
import java.util.*

class DateTimeConverters {
    @TypeConverter
    fun toCalendar(time: Long): Calendar? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long? = calendar?.time?.time
}