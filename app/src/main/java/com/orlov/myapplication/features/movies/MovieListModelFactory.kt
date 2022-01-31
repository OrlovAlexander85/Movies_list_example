package com.orlov.myapplication.features.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orlov.myapplication.data.MovieRepository

class MovieListModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MovieListViewModel(repository) as T
}