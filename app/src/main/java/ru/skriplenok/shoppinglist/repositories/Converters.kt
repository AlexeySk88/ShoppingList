package ru.skriplenok.shoppinglist.repositories

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar?): Long? = calendar?.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long?): Calendar? {
        if (value != null) {
            return Calendar.getInstance().apply { timeInMillis = value }
        }
        return null
    }
}