package com.sevenyes.w5cn07.views.state

import com.sevenyes.w5cn07.models.Jokes

sealed class JokeUIState {
    object LOADING : JokeUIState()
    class SUCCESS(var jokes : Jokes) : JokeUIState()
    class ERROR(var e : Throwable) : JokeUIState()
}