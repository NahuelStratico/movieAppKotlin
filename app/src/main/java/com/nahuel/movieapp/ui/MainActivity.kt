package com.nahuel.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.nahuel.movieapp.R
import com.nahuel.movieapp.core.NowPlayingAdapter
import com.nahuel.movieapp.core.PopularMoviesAdapter
import com.nahuel.movieapp.core.SearchMovieAdapter
import com.nahuel.movieapp.core.TopRatedAdapter
import com.nahuel.movieapp.core.UpcomingAdapter
import com.nahuel.movieapp.data.MovieRepository
import com.nahuel.movieapp.data.modelApi.Movie
import com.nahuel.movieapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity(), OnQueryTextListener, PopularMoviesAdapter.OnPopularMovieClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : PopularMoviesAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter

    private var movieItem = mutableListOf<Movie>()
    private var nowPlayingItem = mutableListOf<Movie>()
    private var searchMovieItem = mutableListOf<Movie>()
    private var upcomingItem = mutableListOf<Movie>()
    private var topRatedItem = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.SearchView.setOnQueryTextListener(this)

        popularMoviesRecycler()
        nowPlayingRecycler()
        initUpcomingRecycler()
        initTopRatedRecycler()
        searchMovieAdapter()


        //Show Popular and
        binding.btnPopular.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val apiKey = getString(R.string.api_key)
                val popularMovies = MovieRepository.service.listPopularMovies(apiKey)

            runOnUiThread {
                binding.RVList.isVisible = true
                binding.rvNowPlaying.isVisible = false
                binding.rvUpcoming.isVisible = false
                binding.rvTopRated.isVisible = false
                binding.rvSearch.isVisible = false

                binding.btnPopular.isSelected = true

                if (binding.btnPopular.isSelected){
                    binding.btnPopular.backgroundTintList = getColorStateList(R.color.bg_selected)
                    binding.btnNowplaying.backgroundTintList = getColorStateList(R.color.bg_card)
                    binding.btnUpcoming.backgroundTintList = getColorStateList(R.color.bg_card)
                    binding.btnTopRated.backgroundTintList = getColorStateList(R.color.bg_card)
                    binding.btnPopular.isSelected = true
                }else{
                    binding.btnPopular.backgroundTintList = getColorStateList(R.color.bg_card)
                    binding.btnPopular.isSelected = false
                }

                if (popularMovies != null) {
                    val movie = popularMovies.results
                    movieItem.clear()
                    movieItem.addAll(movie)
                    adapter.notifyDataSetChanged()

                    Log.i("Nahuel", "Funciona la app")

                } else {
                    Log.e("Nahuel", "No funciona la app")
                }
            }
        }
        }

        //Show Nowplaying
        binding.btnNowplaying.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val apiKey = getString(R.string.api_key)
                val nowPlayingMovies = MovieRepository.service.nowPlayingMovies(apiKey)
                runOnUiThread {
                    binding.rvNowPlaying.isVisible = true
                    binding.RVList.isVisible = false
                    binding.rvUpcoming.isVisible = false
                    binding.rvTopRated.isVisible = false
                    binding.rvSearch.isVisible = false

                    binding.btnNowplaying.isSelected = true

                    if (binding.btnNowplaying.isSelected){
                        binding.btnNowplaying.backgroundTintList = getColorStateList(R.color.bg_selected)
                        binding.btnPopular.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnUpcoming.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnTopRated.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnNowplaying.isSelected = true
                    }else{
                        binding.btnNowplaying.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnNowplaying.isSelected = false
                    }

                    if(nowPlayingMovies != null){
                        val nowPlaying = nowPlayingMovies.results
                        nowPlayingItem.clear()
                        nowPlayingItem.addAll(nowPlaying)
                        nowPlayingAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        //Show Upcoming
        binding.btnUpcoming.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val apiKey = getString(R.string.api_key)
                val upcomingMovie = MovieRepository.service.upcomingMovies(apiKey)

                runOnUiThread {
                    binding.rvUpcoming.isVisible = true
                    binding.rvNowPlaying.isVisible = false
                    binding.RVList.isVisible = false
                    binding.rvTopRated.isVisible = false
                    binding.rvSearch.isVisible = false

                    binding.btnUpcoming.isSelected = true

                    if (binding.btnUpcoming.isSelected){
                        binding.btnUpcoming.backgroundTintList = getColorStateList(R.color.bg_selected)
                        binding.btnNowplaying.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnPopular.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnTopRated.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnUpcoming.isSelected = true
                    }else{
                        binding.btnUpcoming.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnUpcoming.isSelected = false
                    }



                    if(upcomingMovie != null) {
                        val upcomingMovie = upcomingMovie.results
                        upcomingItem.clear()
                        upcomingItem.addAll(upcomingMovie)
                        upcomingAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        //Show Top Rated
        binding.btnTopRated.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val apiKey = getString(R.string.api_key)
                val topRated = MovieRepository.service.topRatedMovies(apiKey)
                runOnUiThread {
                    binding.rvTopRated.isVisible = true
                    binding.rvNowPlaying.isVisible = false
                    binding.RVList.isVisible = false
                    binding.rvUpcoming.isVisible = false
                    binding.rvSearch.isVisible = false

                    binding.btnTopRated.isSelected = true

                    if (binding.btnTopRated.isSelected){
                        binding.btnTopRated.backgroundTintList = getColorStateList(R.color.bg_selected)
                        binding.btnNowplaying.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnPopular.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnUpcoming.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnTopRated.isSelected = true
                    }else{
                        binding.btnTopRated.backgroundTintList = getColorStateList(R.color.bg_card)
                        binding.btnTopRated.isSelected = false
                    }



                    if(topRated != null){
                        val movieTopRated = topRated.results
                        topRatedItem.clear()
                        topRatedItem.addAll(movieTopRated)
                        topRatedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    //Recycler Popular Movies
    private fun popularMoviesRecycler() {
        adapter = PopularMoviesAdapter(movieItem, this)
        binding.RVList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.RVList.adapter = adapter
    }

    //Recycler Now Playing
    private fun nowPlayingRecycler() {
        nowPlayingAdapter = NowPlayingAdapter(nowPlayingItem)
        binding.rvNowPlaying.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvNowPlaying.adapter = nowPlayingAdapter
    }

    //Init Upcoming Recycler
    private fun initUpcomingRecycler() {
        upcomingAdapter = UpcomingAdapter(upcomingItem)
        binding.rvUpcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvUpcoming.adapter = upcomingAdapter
    }

    //init TopRated Recycler
    private fun initTopRatedRecycler(){
        topRatedAdapter = TopRatedAdapter(topRatedItem)
        binding.rvTopRated.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopRated.adapter = topRatedAdapter
    }

    //Search Movie Recycler
    private fun searchMovieAdapter() {
        searchMovieAdapter = SearchMovieAdapter(searchMovieItem)
        binding.rvSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvSearch.adapter = searchMovieAdapter
    }


    //Search Movie
    private fun searchByMovie(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = getString(R.string.api_key)
            val queryMovie = MovieRepository.service.searchMovie("search/movie?query=$query&api_key=$apiKey")
            Log.d("search", "$queryMovie")
            runOnUiThread {
                binding.rvSearch.isVisible = true
                binding.RVList.isVisible = false
                binding.rvNowPlaying.isVisible = false
                binding.rvUpcoming.isVisible = false
                binding.rvTopRated.isVisible = false

                binding.btnTopRated.backgroundTintList = getColorStateList(R.color.bg_card)
                binding.btnNowplaying.backgroundTintList = getColorStateList(R.color.bg_card)
                binding.btnPopular.backgroundTintList = getColorStateList(R.color.bg_card)
                binding.btnUpcoming.backgroundTintList = getColorStateList(R.color.bg_card)

                binding.SearchView.setQuery("", false)
                binding.SearchView.clearFocus()

                if (queryMovie != null) {
                    val movie = queryMovie.results
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

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)

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
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }



}

