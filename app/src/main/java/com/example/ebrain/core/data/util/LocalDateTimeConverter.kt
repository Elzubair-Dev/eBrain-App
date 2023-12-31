package com.example.ebrain.core.data.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun toString(date: LocalDateTime):String{
        return date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(date: String):LocalDateTime{
        return LocalDateTime.parse(date)
    }
}