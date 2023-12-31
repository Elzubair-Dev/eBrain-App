package com.example.ebrain.feature_task.data.util

import androidx.room.TypeConverter
import com.example.ebrain.feature_task.domain.util.Interval

class IntervalConverter {
    @TypeConverter
    fun fromIntervalToString(interval: Interval?):String{
        return interval?.name ?: "null"
    }

    @TypeConverter
    fun fromStringToInterval(value: String): Interval? {
        return if (value == "null") null else Interval.valueOf(value)
    }
}