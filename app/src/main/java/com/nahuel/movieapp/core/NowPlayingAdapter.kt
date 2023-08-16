package com.nahuel.movieapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.movieapp.R
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.NowPlayingItemBinding

class NowPlayingAdapter(var movies: List<Movie>) : RecyclerView.Adapter<NowPlayingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NowPlayingHolder(layoutInflater.inflate(R.layout.now_playing_item,parent,false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: NowPlayingHolder, position: Int) {
        val movie = movies[position]
        holder.render(movie)
    }

}

class NowPlayingHolder(view: View): RecyclerView.ViewHolder(view){

    private val binding = NowPlayingItemBinding.bind(view)

    fun render(movie: Movie){
        Glide.with(binding.ivNowPlaying.context)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            .into(binding.ivNowPlaying)
    }
}