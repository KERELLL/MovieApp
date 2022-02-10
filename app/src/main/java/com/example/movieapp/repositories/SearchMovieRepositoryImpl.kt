package com.example.movieapp.repositories

import com.example.movieapp.network.api.ApiSearchProvider
import com.example.movieapp.network.responses.movies.MoviesResponse

object SearchMovieRepositoryImpl : SearchMovieRepository {
    val apiService = ApiSearchProvider.apiService
    override suspend fun getFoundMovie(movieName: String): MoviesResponse {
        return apiService.getFoundMovie(movieName)
    }
}