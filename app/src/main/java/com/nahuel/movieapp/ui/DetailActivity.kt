package com.nahuel.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.nahuel.movieapp.R
import com.nahuel.movieapp.data.MovieRepository
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)

        if (movie != null) {
            binding.titleDetail.text = movie.title
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdropPath}")
                .into(binding.backdrop)
        }

        CoroutineScope(Dispatchers.IO).launch {
            var movieId = movie?.id.toString()
            val apiKey = getString(R.string.api_key)
            val movieDetail = MovieRepository.service?.detailMovie("movie/$movieId?api_key=$apiKey")
            val creditsMovie = MovieRepository.service?.creditsMovie("movie/$movieId/credits?api_key=$apiKey")

            runOnUiThread {
                if (movieDetail != null && creditsMovie != null) {
                    binding.companies.text = movieDetail.productionCompanies[0].name
                    binding.overView.text = movieDetail.overview
                    binding.genre1.text = movieDetail.genres[0].name
                    binding.genre2.text = movieDetail.genres[1].name


                    Glide.with(binding.characterImg.context)
                        .load("https://image.tmdb.org/t/p/w500/${creditsMovie.cast[0].profilePath}")
                        .into(binding.characterImg)

                    binding.characterName.text = creditsMovie.cast[0].name

                    Glide.with(binding.characterImg2.context)
                        .load("https://image.tmdb.org/t/p/w500/${creditsMovie.cast[1].profilePath}")
                        .into(binding.characterImg2)

                    binding.characterName2.text = creditsMovie.cast[1].name
                }

            }
        }


    }
}