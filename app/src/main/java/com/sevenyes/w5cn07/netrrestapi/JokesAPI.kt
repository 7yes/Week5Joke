package com.sevenyes.w5cn07.netrrestapi

import com.sevenyes.w5cn07.models.Jokes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JokesAPI {

    @GET(RANDOM_PATH)
    suspend fun getJokes (@Path("number") number : Int) : Response<Jokes>

    companion object{
        //http://api.icndb.com/jokes/random/20
        const val BASE_PATH = "http://api.icndb.com/jokes/"
        private const val RANDOM_PATH = "random/{number}"
    }



}