package com.example.miseryreminder.database

import androidx.room.TypeConverter

enum class Status {
    INTERVIEWED,
    REJECTED,
    ACCEPTED,
    PENDING
}

class Converters {
    @TypeConverter
    fun fromStatus(value: Status) = value.name

    @TypeConverter
    fun toStatus(value: String) = Status.valueOf(value)
}