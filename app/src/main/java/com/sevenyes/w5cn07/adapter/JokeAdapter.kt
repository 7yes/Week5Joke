package com.sevenyes.w5cn07.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sevenyes.w5cn07.R
import com.sevenyes.w5cn07.models.Joke

class JokeAdapter(private val jokesList: MutableList<Joke> = mutableListOf()) :
    RecyclerView.Adapter<JokeHolder>() {

    fun appendJokes(newList: List<Joke>) {
        jokesList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return JokeHolder(view)
    }

    override fun onBindViewHolder(holder: JokeHolder, position: Int) =
        holder.bind(jokesList[position])

    override fun getItemCount(): Int {
        return jokesList.size
    }
}

class JokeHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(joke: Joke) {
        view.findViewById<TextView>(R.id.miJokes)
            .text = joke.joke
    }
}