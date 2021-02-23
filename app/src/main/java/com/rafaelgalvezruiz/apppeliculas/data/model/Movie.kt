package com.rafaelgalvezruiz.apppeliculas.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
        val id: Int = -1,
         @SerializedName("adult") //Allow personalize the name of variable
        val adult: Boolean = false,
        val genre_ids: List<Int> = listOf(),
        val backdrop_path: String = "",
        val original_title: String = "",
        val overview: String ="",
        val popularity: Double = -1.0,
        val poster_path: String = "",
        val release_date: String ="",
        val title: String = "",
        val language: String = "",
        val video: Boolean = false,
        val vote_average: Double = -1.0,
        val vote_count: Int = -1
)

data class MovieList(val results: List<Movie> = listOf())