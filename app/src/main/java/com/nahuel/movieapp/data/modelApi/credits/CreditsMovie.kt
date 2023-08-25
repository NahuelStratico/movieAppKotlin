package com.nahuel.movieapp.data.modelApi.credits


import com.google.gson.annotations.SerializedName

data class CreditsMovie(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)