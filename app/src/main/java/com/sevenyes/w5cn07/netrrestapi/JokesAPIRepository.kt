package com.sevenyes.w5cn07.netrrestapi

import com.sevenyes.w5cn07.models.Jokes
import retrofit2.Response


interface IJokesApiRepository {
    suspend fun getJokes(number: Int): Response<Jokes>
}

class JokesAPIRepository (private val jokesAPI: JokesAPI): IJokesApiRepository {
    override suspend fun getJokes(number: Int): Response<Jokes> {
         return   jokesAPI.getJokes()
    }
}