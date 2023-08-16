package com.nahuel.movieapp.data

import com.nahuel.movieapp.data.modelApi.MovieDbResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface MovieAPI {

    //Get Popular
    @GET("movie/popular?")
    fun listPopularMovies(@Query("api_key") apiKey: String): Call<MovieDbResult>

    //Get Now Playing
    @GET("movie/now_playing?")
    fun nowPlayingMovies(@Query("api_key") apiKey: String): Call<MovieDbResult>

    //Search movie
    @GET
    fun searchMovie( @Url url:String) : Call<MovieDbResult>

    //Detail movie
    //https://api.themoviedb.org/3/movie/6?&api_key=b7dc5e020f5261aa73cb34093b069c9a
    //Detail https://api.themoviedb.org/3/movie/{movie_id}




}