package com.example.movieapp.network.responses.movieCredits

data class MovieCreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)