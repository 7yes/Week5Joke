package com.sevenyes.w5cn07.netrrestapi

import com.sevenyes.w5cn07.models.Jokes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokesAPI {

    @GET(RANDOM_PATH)
    suspend fun getJokes (@Path("number") number : Int = RANDOM_JOKE_COUNT) : Response<Jokes>

    @GET(RANDOM_PATH)
    suspend fun getCustom (
        @Path("number") number : Int = RANDOM_JOKE_COUNT,
        @Query("firstName") firstName : String,
        @Query("lastName") lastName : String
    ) : Response<Jokes>

    companion object{
        const val BASE_PATH = "https://api.icndb.com/jokes/"
        const val RANDOM_JOKE_COUNT = 1
        const val RANDOM_JOKE_LIST = 20
        private const val RANDOM_PATH = "random/{number}"
    }
}