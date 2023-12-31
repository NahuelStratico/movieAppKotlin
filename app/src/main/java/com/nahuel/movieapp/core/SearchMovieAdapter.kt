package com.nahuel.movieapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.movieapp.R
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.SearchMovieItemBinding

class SearchMovieAdapter(var movies: List<Movie>) : RecyclerView.Adapter<SearchMovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchMovieHolder(layoutInflater.inflate(R.layout.search_movie_item, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: SearchMovieHolder, position: Int) {
        val movie = movies[position]
        holder.render(movie)
    }

}

class SearchMovieHolder(view:View) : RecyclerView.ViewHolder(view){

    private val binding =  SearchMovieItemBinding.bind(view)

    fun render(movie: Movie){
            Glide.with(binding.ivSearchItem.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .into(binding.ivSearchItem)
    }
}