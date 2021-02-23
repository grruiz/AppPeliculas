package com.rafaelgalvezruiz.apppeliculas.repository

import com.rafaelgalvezruiz.apppeliculas.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies():MovieList
    suspend fun getTopRatedMovies():MovieList
    suspend fun getPopularMovies():MovieList

}