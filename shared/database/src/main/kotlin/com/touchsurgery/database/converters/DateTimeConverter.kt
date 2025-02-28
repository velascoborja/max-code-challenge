package com.touchsurgery.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

object DateTimeConverter {

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Instant?): Long? {
        return date?.toEpochMilliseconds()
    }

    @TypeConverter
    @JvmStatic
    fun toDate(date: Long?): Instant? {
        return if (date == null) null else {
            Instant.fromEpochMilliseconds(date)
        }
    }
}
