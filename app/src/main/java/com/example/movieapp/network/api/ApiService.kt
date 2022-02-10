package com.example.movieapp.network.api

import com.example.movieapp.network.responses.movieCredits.MovieCreditsResponse
import com.example.movieapp.network.responses.movieDetails.MovieDetailsResponse
import com.example.movieapp.network.responses.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("popular?api_key=631cd9d3aac8d1cd4b6fcaa498530108")
    suspend fun getPopularMovies() : MoviesResponse

    @GET("{movie_id}?api_key=631cd9d3aac8d1cd4b6fcaa498530108")
    suspend fun getMovieDetails(@Path("movie_id")movieId : Int) : MovieDetailsResponse

    @GET("{movie_id}/credits?api_key=631cd9d3aac8d1cd4b6fcaa498530108")
    suspend fun getMovieCredits(@Path("movie_id")movieId : Int) : MovieCreditsResponse

    @GET("movie?api_key=631cd9d3aac8d1cd4b6fcaa498530108")
    suspend fun getFoundMovie(@Query("query") movieName: String): MoviesResponse
}