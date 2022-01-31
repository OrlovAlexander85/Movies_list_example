package com.orlov.myapplication.features.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlov.myapplication.data.MovieRepository
import com.orlov.myapplication.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies
    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _movies.value = repository.loadMovies()
        }
    }
}