package com.example.movieapp.ui.moviedetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.ui.moviecredits.MovieCreditsAdapter
import com.example.movieapp.ui.moviecredits.MovieCreditsViewModel
import com.example.movieapp.ui.moviecredits.UIStateMovieCredits
import com.example.movieapp.ui.movies.UIStateMovies
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var movieCreditsAdapter: MovieCreditsAdapter
    private lateinit var movieCreditsViewModel: MovieCreditsViewModel
    private val args : MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        movieCreditsViewModel = ViewModelProvider(this).get(MovieCreditsViewModel::class.java)

        movieDetailsViewModel.movieDetailsStateFlowPublic.onEach {
            when(it){
                is UIStateMovieDetails.Loading ->{
                    Toast.makeText(requireContext(), "Data is loading", Toast.LENGTH_LONG).show()
                }
                is UIStateMovieDetails.Success ->{
                    Glide.with(requireContext()).load(it.movieDetails.movieBackground).into(binding.movieImageView)
                    binding.titleTextView.text = it.movieDetails.movieTitle
                    binding.releaseDateTextView.text = "Year Released: " + it.movieDetails.releaseDate.split('-')[0]
                    binding.runtimeTextView.text = "Length: " + (it.movieDetails.runtime / 100).toString() + "h " + (it.movieDetails.runtime % 100).toString() + "min"
                    binding.voteAverageTextView.text = "Rating: " + it.movieDetails.voteAverage.toString()
                    binding.voteCountTextView.text = it.movieDetails.voteCount.toString() + " Votes"
                    binding.overviewTextView.text = it.movieDetails.overview
                }
                is UIStateMovieDetails.Error ->{
                    Toast.makeText(requireContext(),it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

        movieCreditsViewModel.movieCreditsStateFlowPublic.onEach {
            when(it){
                is UIStateMovieCredits.Loading ->{
                    Toast.makeText(requireContext(), "Data is loading", Toast.LENGTH_LONG).show()
                }
                is UIStateMovieCredits.Success ->{
                    movieCreditsAdapter.movieCreditsList.clear()
                    for (i in 0 until it.movieCredits.size){
                        movieCreditsAdapter.movieCreditsList.add(
                            it.movieCredits[i]
                        )
                    }
                    movieCreditsAdapter.notifyDataSetChanged()
                }
                is UIStateMovieCredits.Error ->{
                    Toast.makeText(requireContext(),it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

        movieDetailsViewModel.getPopularMovies(args.movieId)
        movieCreditsAdapter = MovieCreditsAdapter(requireContext())
        binding.actorsRecyclerView.adapter = movieCreditsAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.actorsRecyclerView.layoutManager = layoutManager
        movieCreditsViewModel.getMovieCredits(args.movieId)
    }

}