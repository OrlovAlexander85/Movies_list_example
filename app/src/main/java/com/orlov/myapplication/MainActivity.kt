package com.orlov.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orlov.myapplication.data.JsonMovieRepository
import com.orlov.myapplication.data.MovieRepository
import com.orlov.myapplication.data.MovieRepositoryProvider
import com.orlov.myapplication.features.movie_details.MovieDetailsFragment
import com.orlov.myapplication.features.movies.MovieListFragment
import com.orlov.myapplication.model.Movie

class MainActivity : AppCompatActivity(),
    MovieListFragment.MovieListItemClickListener,
    MovieDetailsFragment.MovieDetailsBackClickListener,
    MovieRepositoryProvider {

    private val jsonMovieRepository = JsonMovieRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }
    }

    override fun onMovieSelected(movie: Movie) {
        routeToMovieDetails(movie)
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MovieListFragment.create(),
                MovieListFragment::class.java.simpleName
            )
            .addToBackStack("trans:${MovieListFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeToMovieDetails(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                MovieDetailsFragment.create(movie.id),
                MovieDetailsFragment::class.java.simpleName
            )
            .addToBackStack("trans:${MovieDetailsFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = jsonMovieRepository
}