package ru.skriplenok.shoppinglist.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import ru.skriplenok.shoppinglist.repositories.Converters
import java.util.*

class ConvertersTest {

    private val calendar = Calendar.getInstance()
    private val converters = Converters()

    @Test
    fun testCalendarToDatestamp() {
        assertEquals(calendar.timeInMillis, converters.calendarToDatestamp(calendar))
        assertNull(converters.calendarToDatestamp(null))
    }

    @Test
    fun testDatestampToCalendar() {
        assertEquals(converters.datestampToCalendar(calendar.timeInMillis), calendar)
        assertNull(converters.datestampToCalendar(null))
    }
}