package com.sevenyes.w5cn07.di

import com.sevenyes.w5cn07.netrrestapi.IJokesApiRepository
import com.sevenyes.w5cn07.netrrestapi.JokesAPI
import com.sevenyes.w5cn07.netrrestapi.JokesAPIRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val rest = module {

    fun providesInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun providesOkHttpClient(
        loginInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient
        .Builder()
        .addInterceptor(loginInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): JokesAPI {
        return Retrofit.Builder()
            .baseUrl(JokesAPI.BASE_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(JokesAPI::class.java)
    }

    fun provideAPIRepository(jokesAPI: JokesAPI): IJokesApiRepository {
        return JokesAPIRepository(jokesAPI)
    }

    single { providesInterceptor() }
    single { providesOkHttpClient(get()) }
    single { providesRetrofit(get()) }
    single { provideAPIRepository(get()) }

}

