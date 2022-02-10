package com.example.movieapp.repositories

import com.example.movieapp.network.api.ApiProvider
import com.example.movieapp.network.responses.movieCredits.MovieCreditsResponse
import com.example.movieapp.network.responses.movieDetails.MovieDetailsResponse
import com.example.movieapp.network.responses.movies.MoviesResponse

object MoviesRepositoryImpl : MoviesRepository {
    private val apiService = ApiProvider.apiService

    override suspend fun getPopularMovies(): MoviesResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse {
        return apiService.getMovieCredits(movieId)
    }
}