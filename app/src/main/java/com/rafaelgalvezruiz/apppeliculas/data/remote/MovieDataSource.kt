package com.rafaelgalvezruiz.apppeliculas.data.remote

import com.rafaelgalvezruiz.apppeliculas.application.AppConstants
import com.rafaelgalvezruiz.apppeliculas.data.model.MovieList
import com.rafaelgalvezruiz.apppeliculas.repository.WebService


class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}