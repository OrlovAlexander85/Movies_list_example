package com.orlov.myapplication.features.movie_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.orlov.myapplication.R
import com.orlov.myapplication.data.MovieRepositoryProvider
import com.orlov.myapplication.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var listener: MovieDetailsBackClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getSerializable(PARAM_MOVIE_ID) as? Int ?: return

        view.findViewById<RecyclerView>(R.id.actors_recycler_view).apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            this.adapter = ActorListAdapter()
        }

        view.findViewById<View>(R.id.backTextView)?.setOnClickListener {
            listener?.onMovieDeselected()
        }

        viewModel.loadDetails(movieId)

        lifecycleScope.launch {
            viewModel.movie.collect { movie ->
                movie?.let { bindUI(view, it) } ?: showMovieNotFoundError()
            }
        }
    }

    private fun showMovieNotFoundError() {
        Toast.makeText(
            requireContext(),
            "Movie not found",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun bindUI(view: View, movie: Movie) {
        updateMovieDetailsInfo(movie)
        val adapter =
            view.findViewById<RecyclerView>(R.id.actors_recycler_view).adapter as ActorListAdapter
        adapter.submitList(movie.actors)
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    private fun updateMovieDetailsInfo(movie: Movie) {
        view?.findViewById<ImageView>(R.id.topImageView)?.load(movie.detailImageUrl)

        view?.findViewById<TextView>(R.id.pg_age)?.text = "${movie.pgAge}+"

        view?.findViewById<TextView>(R.id.movieTitleTextView)?.text = movie.title
        view?.findViewById<TextView>(R.id.genreTextView)?.text =
            movie.genres.joinToString { it.name }
        view?.findViewById<TextView>(R.id.reviews_count)?.text = "${movie.reviewCount} Reviews"
        view?.findViewById<TextView>(R.id.storylineFullTextView)?.text = movie.storyLine

    }

    interface MovieDetailsBackClickListener {

        fun onMovieDeselected()
    }

    companion object {

        private const val PARAM_MOVIE_ID = "movie_id"

        fun create(movieId: Int) = MovieDetailsFragment().also {
            val args = bundleOf(
                PARAM_MOVIE_ID to movieId
            )
            it.arguments = args
        }
    }
}