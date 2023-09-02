package com.nahuel.movieapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.movieapp.R
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.TopRatedItemBinding

class TopRatedAdapter(
    private var movies: List<Movie>,
    private val itemClickTopRatedListener: OnItemClickTopRatedListener
    ):RecyclerView.Adapter<TopRatedAdapter.TopRaterViewHolder>() {

    interface OnItemClickTopRatedListener{
        fun onItemTopRated(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRaterViewHolder {
        return TopRaterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.top_rated_item, parent,false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: TopRaterViewHolder, position: Int) {
        holder.render(movies[position])
    }

    inner class TopRaterViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = TopRatedItemBinding.bind(view)

        fun render(movie: Movie){
            binding.ivTopRated.setOnClickListener { itemClickTopRatedListener.onItemTopRated(movie) }

            Glide.with(binding.ivTopRated.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .into(binding.ivTopRated)
        }
    }
}