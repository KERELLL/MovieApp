package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.repositories.MoviesRepository
import com.example.movieapp.repositories.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl
    private val movieDetailsStateFlow: MutableStateFlow<UIStateMovieDetails> = MutableStateFlow(UIStateMovieDetails.Loading)

    val movieDetailsStateFlowPublic = movieDetailsStateFlow.asStateFlow()

    fun getPopularMovies(movieId: Int){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    moviesRepository.getMovieDetails(movieId)
                }
                val partOfUrl = "https://image.tmdb.org/t/p/original"
                val movieDetails = MovieDetails(
                    movieBackground = partOfUrl + response.backdrop_path,
                    movieTitle = response.title,
                    releaseDate = response.release_date,
                    runtime = response.runtime,
                    voteAverage = response.vote_average,
                    voteCount = response.vote_count,
                    overview = response.overview
                )
                movieDetailsStateFlow.value = UIStateMovieDetails.Success(movieDetails)
            }catch (e: Exception){
                movieDetailsStateFlow.value = UIStateMovieDetails.Error(e)
            }
        }
    }
}

sealed class UIStateMovieDetails{
    object Loading : UIStateMovieDetails()
    class Error(val e: Exception) : UIStateMovieDetails()
    class Success(val movieDetails: MovieDetails) : UIStateMovieDetails()
}