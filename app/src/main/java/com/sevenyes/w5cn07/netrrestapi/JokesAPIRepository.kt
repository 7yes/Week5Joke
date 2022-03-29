package com.sevenyes.w5cn07.netrrestapi

import com.sevenyes.w5cn07.models.Jokes
import retrofit2.Response


interface IJokesApiRepository {
    suspend fun getJokes(number: Int = JokesAPI.RANDOM_JOKE_COUNT): Response<Jokes>
    suspend fun getCustom(
        firstName: String,
        lastName: String
    ): Response<Jokes>
}

class JokesAPIRepository(private val jokesAPI: JokesAPI) : IJokesApiRepository {

    override suspend fun getJokes(number: Int): Response<Jokes> {
        return jokesAPI.getJokes()
    }

    override suspend fun getCustom(
        firstName: String,
        lastName: String
    ): Response<Jokes> {
        return jokesAPI.getCustom(JokesAPI.RANDOM_JOKE_COUNT, firstName, lastName)
    }
}