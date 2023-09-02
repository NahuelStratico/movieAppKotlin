package com.nahuel.movieapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.movieapp.R
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.PopularMovieItemBinding

class PopularMoviesAdapter(
    private var movies: List<Movie>,
    private val itemClickListener: OnPopularMovieClickListener
    ) : RecyclerView.Adapter<PopularMoviesAdapter.ListMovieHolder>() {

    interface OnPopularMovieClickListener{
        fun onItemClick(movie: Movie)

    }

   // Pinto la vista en Recycler
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListMovieHolder(layoutInflater.inflate(R.layout.popular_movie_item, parent, false))
    }
    // Devuelvo el tamaño de la lista
    override fun getItemCount(): Int = movies.size


    // Este metodo pasa por cada item y llama a function viewHolder
    override fun onBindViewHolder(holder: ListMovieHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    inner class ListMovieHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = PopularMovieItemBinding.bind(view)

        fun bind(movie: Movie) {
            binding.ivItem.setOnClickListener { itemClickListener.onItemClick(movie) }

            Glide.with(binding.ivItem.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .into(binding.ivItem)

        }

    }

}





