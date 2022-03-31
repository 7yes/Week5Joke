package com.sevenyes.w5cn07.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.room.Room
import com.sevenyes.w5cn07.database.DataBaseRepository
import com.sevenyes.w5cn07.database.IDataBaseRepository
import com.sevenyes.w5cn07.database.JokeDataBase
import com.sevenyes.w5cn07.database.JokesDao
import com.sevenyes.w5cn07.netrrestapi.IJokesApiRepository
import com.sevenyes.w5cn07.netrrestapi.JokesAPI
import com.sevenyes.w5cn07.netrrestapi.JokesAPIRepository
import com.sevenyes.w5cn07.services.NetworkMonitor
import com.sevenyes.w5cn07.viewmodels.JokesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
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

    fun provideAPIRepository(jokesAPI: JokesAPI):
            IJokesApiRepository {
        return JokesAPIRepository(jokesAPI)
    }

    single { providesInterceptor() }
    single { providesOkHttpClient(get()) }
    single { providesRetrofit(get()) }
    single { provideAPIRepository(get()) }

}

val viewModelModules = module {
    viewModel { JokesViewModel(get(), get()) }
}

val servicesModule = module {

    fun providesNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
    }

    fun providesConnectivityManager(
        context: Context
    ): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
    }

    fun providesNetworkMonitor(
        connectivityManager: ConnectivityManager,
        networkRequest: NetworkRequest
    ): NetworkMonitor {
        return NetworkMonitor(connectivityManager, networkRequest)
    }

    single { providesNetworkRequest() }
    single { providesConnectivityManager(get()) }
    single { providesNetworkMonitor(get(), get()) }
}

val dataBaseModule = module {

    fun providesDataBase(context: Context): JokeDataBase =
        Room.databaseBuilder(
            context,
            JokeDataBase::class.java, "jokesDB"
        ).build()

    fun providesJokesDao(dataBase: JokeDataBase) = dataBase.jokesDao()

    fun providesRepository(jokesDao: JokesDao): IDataBaseRepository = DataBaseRepository(jokesDao)

    single { providesDataBase(get()) }
    single { providesJokesDao(get()) }
    single { providesRepository(get()) }
}