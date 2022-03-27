package com.sevenyes.w5cn07.models


import com.google.gson.annotations.SerializedName

data class Jokes(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val jokes: List<Joke>
)