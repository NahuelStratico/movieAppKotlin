package com.nahuel.movieapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.movieapp.R
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.UpcomingItemBinding

class UpcomingAdapter( var movies: List<Movie>) : RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.upcoming_item, parent,false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.render(movies[position])
    }

    inner class UpcomingViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = UpcomingItemBinding.bind(view)

        fun render(movie: Movie){
            Glide.with(binding.ivUpcoming.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .into(binding.ivUpcoming)
        }
    }
}