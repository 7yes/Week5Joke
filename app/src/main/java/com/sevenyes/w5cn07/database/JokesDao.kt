package com.sevenyes.w5cn07.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenyes.w5cn07.models.Joke

@Dao
interface JokesDao {
    @Query("SELECT * FROM joke")
    suspend fun readAll() : List<Joke>

    @Insert(entity = Joke::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun  writeAll (fullList : List<Joke>)
}

