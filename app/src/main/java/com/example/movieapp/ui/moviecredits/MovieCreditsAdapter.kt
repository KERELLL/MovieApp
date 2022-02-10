package com.example.movieapp.ui.moviecredits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieCredits

class MovieCreditsAdapter(var context: Context) :
    RecyclerView.Adapter<MovieCreditsAdapter.ViewHolder>() {

    var movieCreditsList: MutableList<MovieCredits> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_credits_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(movieCreditsList[position].backgroundPhoto).into(holder.creditImage)
        holder.originalName.text = movieCreditsList[position].originalName
        holder.character.text = movieCreditsList[position].character
    }

    override fun getItemCount(): Int {
        return movieCreditsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val creditImage: ImageView = itemView.findViewById(R.id.backgroundCreditImageView)
        val originalName: TextView = itemView.findViewById(R.id.originalNameTextView)
        val character: TextView = itemView.findViewById(R.id.characterTextView)
    }
}