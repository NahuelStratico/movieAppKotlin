package com.nahuel.movieapp.data

import com.nahuel.movieapp.data.modelApi.MovieDbResult
import com.nahuel.movieapp.data.modelApi.MovieDetail
import com.nahuel.movieapp.data.modelApi.credits.CreditsMovie

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface MovieAPI {


    @GET("movie/popular?")
    suspend fun listPopularMovies(@Query("api_key") apiKey: String): MovieDbResult

    @GET("movie/now_playing?")
    suspend fun nowPlayingMovies(@Query("api_key") apiKey: String): MovieDbResult

    @GET("movie/upcoming?")
    suspend fun upcomingMovies(@Query("api_key") apiKey: String) : MovieDbResult

    @GET("movie/top_rated?")
    suspend fun topRatedMovies(@Query("api_key") apiKey: String) : MovieDbResult

    //Search movie
    @GET
    suspend fun searchMovie( @Url url:String ) : MovieDbResult

    @GET
    suspend fun detailMovie( @Url url:String ) : MovieDetail

    @GET
    suspend fun creditsMovie( @Url url:String ) : CreditsMovie

    //Credits
    //https://api.themoviedb.org/3/movie/976573/credits?api_key=b7dc5e020f5261aa73cb34093b069c9a






}