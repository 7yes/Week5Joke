package com.sevenyes.w5cn07.database

import com.sevenyes.w5cn07.models.Joke

interface IDataBaseRepository {
   suspend fun readAll() : List<Joke>
   suspend fun  writeAll (fullList : List<Joke>)
}