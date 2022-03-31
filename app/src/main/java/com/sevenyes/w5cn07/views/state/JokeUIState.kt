package com.sevenyes.w5cn07.views.state

import com.sevenyes.w5cn07.models.Joke
import com.sevenyes.w5cn07.models.Jokes

sealed class JokeUIState {
    object LOADING : JokeUIState()
    class SUCCESS(var jokes : List<Joke>) : JokeUIState()
    class ERROR(var e : Throwable) : JokeUIState()
}