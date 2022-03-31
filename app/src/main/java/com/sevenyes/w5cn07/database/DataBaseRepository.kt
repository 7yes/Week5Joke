package com.sevenyes.w5cn07.database

import com.sevenyes.w5cn07.models.Joke

class DataBaseRepository (private val jokesDao: JokesDao): IDataBaseRepository {
    override suspend fun readAll(): List<Joke> {
       return jokesDao.readAll()
    }

    override suspend fun writeAll(fullList: List<Joke>) {
        jokesDao.writeAll(fullList)
    }

}