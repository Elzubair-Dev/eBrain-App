package com.example.ebrain.core.data.util

import androidx.room.TypeConverter
import com.example.ebrain.core.domain.util.Privacy

class PrivacyConverter {
    @TypeConverter
    fun fromPrivacyToString(privacy: Privacy):String{
        return if (privacy == Privacy.Public) "public" else "private"
    }

    @TypeConverter
    fun fromStringToPrivacy(value: String):Privacy{
        return if (value == "public") Privacy.Public else Privacy.Private
    }
}