package com.sevenyes.w5cn07.netrrestapi

import android.util.Log
import com.sevenyes.w5cn07.models.Jokes
import retrofit2.Response


interface IJokesApiRepository {
    suspend fun getJokes(number: Int = JokesAPI.RANDOM_JOKE_COUNT, explicit : String): Response<Jokes>
    suspend fun getCustom(
        firstName: String,
        lastName: String,
        explicit : String
    ): Response<Jokes>
}

class JokesAPIRepository(private val jokesAPI: JokesAPI) : IJokesApiRepository {

    override suspend fun getJokes(number: Int, explicit: String): Response<Jokes> {
        Log.d("TAG", "in get Jokes")
        return jokesAPI.getJokes(number,listOf(JokesAPI.EXPLICIT).toString())
    }

    override suspend fun getCustom(
        firstName: String,
        lastName: String,
        explicit : String
    ): Response<Jokes> {
        return jokesAPI.getCustom(JokesAPI.RANDOM_JOKE_COUNT, firstName, lastName,explicit)
    }
}