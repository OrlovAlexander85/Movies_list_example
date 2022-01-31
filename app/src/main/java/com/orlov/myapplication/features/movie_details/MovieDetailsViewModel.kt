package com.orlov.myapplication.features.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlov.myapplication.data.MovieRepository
import com.orlov.myapplication.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            _movie.value = repository.loadMovie(movieId)
        }
    }
}