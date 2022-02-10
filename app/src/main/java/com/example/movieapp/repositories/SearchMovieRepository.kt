package com.example.movieapp.repositories

import com.example.movieapp.network.responses.movies.MoviesResponse

interface SearchMovieRepository {

    suspend fun getFoundMovie(movieName : String): MoviesResponse
}