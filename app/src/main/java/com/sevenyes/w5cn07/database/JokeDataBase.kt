package com.sevenyes.w5cn07.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sevenyes.w5cn07.models.Joke

@Database(
    entities = [Joke::class]
    , version = DB_VERSION
)

@TypeConverters(ListToStringConverters::class)
abstract class JokeDataBase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}

private const val DB_VERSION = 1