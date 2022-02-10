package com.example.movieapp.repositories

import com.example.movieapp.network.responses.movieCredits.MovieCreditsResponse
import com.example.movieapp.network.responses.movieDetails.MovieDetailsResponse
import com.example.movieapp.network.responses.movies.MoviesResponse

interface MoviesRepository {

    suspend fun getPopularMovies() : MoviesResponse

    suspend fun getMovieDetails(movieId : Int) : MovieDetailsResponse

    suspend fun getMovieCredits(movieId: Int) : MovieCreditsResponse
}