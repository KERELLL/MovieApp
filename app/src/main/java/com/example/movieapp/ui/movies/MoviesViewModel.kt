package com.example.movieapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MoviesRepository
import com.example.movieapp.repositories.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl
    private val moviesStateFlow: MutableStateFlow<UIStateMovies> = MutableStateFlow(UIStateMovies.Loading)

    val moviesStateFlowPublic = moviesStateFlow.asStateFlow()

    fun getPopularMovies(){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    moviesRepository.getPopularMovies()
                }
                val moviesList = mutableListOf<Movie>()
                for(i in response.results.indices){
                    moviesList.add(Movie("https://image.tmdb.org/t/p/original" + response.results[i].backdrop_path, response.results[i].title, response.results[i].id))
                }
                moviesStateFlow.value = UIStateMovies.Success(moviesList)
            }catch (e: Exception){
                moviesStateFlow.value = UIStateMovies.Error(e)
            }
        }
    }
}

sealed class UIStateMovies{
    object Loading : UIStateMovies()
    class Error(val e: Exception) : UIStateMovies()
    class Success(val movies: MutableList<Movie>) : UIStateMovies()
}