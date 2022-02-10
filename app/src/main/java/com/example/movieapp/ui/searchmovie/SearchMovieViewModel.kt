package com.example.movieapp.ui.searchmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.SearchMovieRepository
import com.example.movieapp.repositories.SearchMovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchMovieViewModel : ViewModel() {
    private val searchMovieRepository: SearchMovieRepository = SearchMovieRepositoryImpl
    private val moviesStateFlow: MutableStateFlow<UIStateSearchMovie> = MutableStateFlow(UIStateSearchMovie.Loading)

    val moviesStateFlowPublic = moviesStateFlow.asStateFlow()

    fun getFoundMovie(movieName : String){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    searchMovieRepository.getFoundMovie(movieName)
                }
                val moviesList = mutableListOf<Movie>()
                for(i in response.results.indices){
                    moviesList.add(Movie("https://image.tmdb.org/t/p/original" + response.results[i].backdrop_path, response.results[i].title, response.results[i].id))
                }
                moviesStateFlow.value = UIStateSearchMovie.Success(moviesList)
            }catch (e: Exception){
                moviesStateFlow.value = UIStateSearchMovie.Error(e)
            }
        }
    }
}

sealed class UIStateSearchMovie{
    object Loading : UIStateSearchMovie()
    class Error(val e: Exception) : UIStateSearchMovie()
    class Success(val movies: MutableList<Movie>) : UIStateSearchMovie()
}