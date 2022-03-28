package com.sevenyes.w5cn07

import android.app.Application
import com.sevenyes.w5cn07.di.rest
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class JokesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JokesApp)
            modules(listOf(
                rest
            ))
        }
    }
}