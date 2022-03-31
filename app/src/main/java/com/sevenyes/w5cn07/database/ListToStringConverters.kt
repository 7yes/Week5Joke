package com.sevenyes.w5cn07.database

import androidx.room.TypeConverter

class ListToStringConverters {
    @TypeConverter
    fun fromList(stringList: List<String>): String = stringList.joinToString ()

    @TypeConverter
    fun fromString(string: String): List<String> =
        string.split(",").toList()
}