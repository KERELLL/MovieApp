package com.example.movieapp.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.ui.searchmovie.SearchMovieViewModel
import com.example.movieapp.ui.searchmovie.UIStateSearchMovie
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var searchMovieViewModel: SearchMovieViewModel
    private lateinit var popularMoviesListAdapter: PopularMoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        searchMovieViewModel = ViewModelProvider(this).get(SearchMovieViewModel::class.java)

        moviesViewModel.moviesStateFlowPublic.onEach {
            when(it){
                is UIStateMovies.Loading ->{
                    Toast.makeText(requireContext(), "Data is loading", Toast.LENGTH_LONG).show()
                }
                is UIStateMovies.Success ->{
                    popularMoviesListAdapter.popularMoviesList.clear()
                    for (i in 0 until it.movies.size){
                        popularMoviesListAdapter.popularMoviesList.add(
                            it.movies[i]
                        )
                    }
                    popularMoviesListAdapter.notifyDataSetChanged()
                }
                is UIStateMovies.Error ->{
                    Toast.makeText(requireContext(),it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

        searchMovieViewModel.moviesStateFlowPublic.onEach {
            when(it){
                is UIStateSearchMovie.Loading ->{
                    Toast.makeText(requireContext(), "Data is loading", Toast.LENGTH_LONG).show()
                }
                is UIStateSearchMovie.Success ->{
                    popularMoviesListAdapter.popularMoviesList.clear()
                    for (i in 0 until it.movies.size){
                        popularMoviesListAdapter.popularMoviesList.add(
                            it.movies[i]
                        )
                    }
                    popularMoviesListAdapter.notifyDataSetChanged()
                }
                is UIStateSearchMovie.Error ->{
                    Toast.makeText(requireContext(),it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

        popularMoviesListAdapter = PopularMoviesListAdapter(requireContext()){
            val movieId = it
            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movieId)
            findNavController().navigate(action)
        }



        binding.popularMoviesRecyclerView.adapter = popularMoviesListAdapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.popularMoviesRecyclerView.layoutManager = gridLayoutManager

        binding.buttonSend.setOnClickListener {
            if(binding.editTextSearch.text.isNullOrBlank()){
                moviesViewModel.getPopularMovies()
            }
            else{
                searchMovieViewModel.getFoundMovie(binding.editTextSearch.text.toString())
            }
        }
        moviesViewModel.getPopularMovies()
    }


}