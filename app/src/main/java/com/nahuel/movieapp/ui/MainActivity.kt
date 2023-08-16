package com.nahuel.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nahuel.movieapp.R
import com.nahuel.movieapp.core.NowPlayingAdapter
import com.nahuel.movieapp.core.PopularMoviesAdapter
import com.nahuel.movieapp.core.SearchMovieAdapter
import com.nahuel.movieapp.data.MovieRepository
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : PopularMoviesAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var searchMovieAdapter: SearchMovieAdapter

    private var movieItem = mutableListOf<Movie>()
    private var nowPlayingItem = mutableListOf<Movie>()
    private var searchMovieItem = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.SearchView.setOnQueryTextListener(this)



        popularMoviesRecycler()
        nowPlayingRecycler()
        searchMovieAdapter()


        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieRepository.service.listPopularMovies(apiKey)
            val nowPlayingMovies = MovieRepository.service.nowPlayingMovies(apiKey)
            val body = popularMovies.execute().body()
            val nowPlaying = nowPlayingMovies.execute().body()
            runOnUiThread {
                if (body != null ){
                    val movie = body?.results ?: emptyList()
                    val nowPlaying = nowPlaying?.results ?: emptyList()
                    Log.d("popular", "$movie")
                    Log.d("nowPlaying", "$nowPlaying")
                    movieItem.clear()
                    movieItem.addAll(movie)
                    nowPlayingItem.clear()
                    nowPlayingItem.addAll(nowPlaying)
                    adapter.notifyDataSetChanged()
                    nowPlayingAdapter.notifyDataSetChanged()
                    showToast()


                    //Log.i("Nahuel", "${moviesAdapter.movies}")
                    Log.i("Nahuel", "Funciona la app")
                }
                else {
                    showToastError()
                    Log.e("Nahuel", "No funciona la app")
                }
            }
        }

    }


    //Recycler Popular Movies
    private fun popularMoviesRecycler() {
        adapter = PopularMoviesAdapter(movieItem)
        binding.RVList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.RVList.adapter = adapter
    }

    //Recycler Now Playing
    private fun nowPlayingRecycler() {
        nowPlayingAdapter = NowPlayingAdapter(nowPlayingItem)
        binding.rvNowPlaying.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvNowPlaying.adapter = nowPlayingAdapter
    }

    //Search Movie
    private fun searchMovieAdapter() {
        searchMovieAdapter = SearchMovieAdapter(searchMovieItem)
        binding.rvSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvSearch.adapter = searchMovieAdapter
    }

    private fun searchByMovie(query: String){
        Log.i("nahuel","estoy dentro")
        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = getString(R.string.api_key)
            val call = MovieRepository.service.searchMovie("search/movie?query=$query&api_key=$apiKey")
            val nameMovie = call.execute().body()
            Log.d("search", "$nameMovie")
            Log.e("error", "hay un error")
            runOnUiThread {
                if (nameMovie != null) {
                    val movie = nameMovie?.results ?: emptyList()
                    Log.d("search", "$movie")
                    searchMovieItem.clear()
                    searchMovieItem.addAll(movie)
                    searchMovieAdapter.notifyDataSetChanged()
                }else{
                    showToastError()
                }
                hideKeyBoard()
            }
        }
    }

    private fun hideKeyBoard() {
        val inm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        if(!query.isNullOrEmpty()){
            searchByMovie(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    private fun showToastError() {
        Toast.makeText(this, "Hubo un error", Toast.LENGTH_LONG).show()
    }

    private fun showToast() {
        Toast.makeText(this, "la api se pinta", Toast.LENGTH_LONG).show()
    }

}

