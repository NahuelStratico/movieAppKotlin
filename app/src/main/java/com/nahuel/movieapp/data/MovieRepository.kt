package com.nahuel.movieapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Client
object MovieRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val service: MovieAPI = retrofit.create(MovieAPI::class.java)

}