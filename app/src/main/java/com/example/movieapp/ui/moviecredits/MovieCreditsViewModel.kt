package com.example.movieapp.ui.moviecredits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieCredits
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.repositories.MoviesRepository
import com.example.movieapp.repositories.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieCreditsViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl
    private val movieCreditsStateFlow: MutableStateFlow<UIStateMovieCredits> = MutableStateFlow(UIStateMovieCredits.Loading)

    val movieCreditsStateFlowPublic = movieCreditsStateFlow.asStateFlow()

    fun getMovieCredits(movieId: Int){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    moviesRepository.getMovieCredits(movieId)
                }
                val partOfUrl = "https://image.tmdb.org/t/p/original"
                val movieCreditsList = mutableListOf<MovieCredits>()
                for (i in response.cast.indices){
                    movieCreditsList.add(
                        MovieCredits(
                        originalName = response.cast[i].original_name,
                        character = response.cast[i].character,
                        backgroundPhoto = partOfUrl + response.cast[i].profile_path
                    ))
                }
                movieCreditsStateFlow.value = UIStateMovieCredits.Success(movieCreditsList)
            }catch (e: Exception){
                movieCreditsStateFlow.value = UIStateMovieCredits.Error(e)
            }
        }
    }
}

sealed class UIStateMovieCredits{
    object Loading : UIStateMovieCredits()
    class Error(val e: Exception) : UIStateMovieCredits()
    class Success(val movieCredits: MutableList<MovieCredits>) : UIStateMovieCredits()
}