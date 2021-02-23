package com.rafaelgalvezruiz.apppeliculas.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.rafaelgalvezruiz.apppeliculas.R
import com.rafaelgalvezruiz.apppeliculas.core.Resource
import com.rafaelgalvezruiz.apppeliculas.data.model.Movie
import com.rafaelgalvezruiz.apppeliculas.data.remote.MovieDataSource
import com.rafaelgalvezruiz.apppeliculas.databinding.FragmentMovieBinding
import com.rafaelgalvezruiz.apppeliculas.databinding.TopRateMoviesRowBinding
import com.rafaelgalvezruiz.apppeliculas.presentation.MovieViewModel
import com.rafaelgalvezruiz.apppeliculas.presentation.MoviewViewModelFactory
import com.rafaelgalvezruiz.apppeliculas.repository.MovieRepositoryImpl
import com.rafaelgalvezruiz.apppeliculas.repository.RetrofitClient
import com.rafaelgalvezruiz.apppeliculas.repository.WebService
import com.rafaelgalvezruiz.apppeliculas.ui.movie.adapters.MovieAdapter
import com.rafaelgalvezruiz.apppeliculas.ui.movie.adapters.concat.PopularConcatAdapter
import com.rafaelgalvezruiz.apppeliculas.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.rafaelgalvezruiz.apppeliculas.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MoviewViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webService)
            )
        )
    }
    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fecthMainScreenMovies().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    it.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    it.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    it.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${it.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path, movie.backdrop_path, movie.vote_average.toFloat(), movie.vote_count,
            movie.overview, movie.title, movie.language, movie.release_date
        )
        findNavController().navigate(action)
    }
}