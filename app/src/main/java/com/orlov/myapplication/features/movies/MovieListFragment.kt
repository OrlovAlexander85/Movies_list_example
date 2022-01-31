package com.orlov.myapplication.features.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlov.myapplication.R
import com.orlov.myapplication.data.MovieRepositoryProvider
import com.orlov.myapplication.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels {
        MovieListModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }
    private var listener: MovieListItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieListItemClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recyclerMovies).apply {
            this.layoutManager = GridLayoutManager(this.context, 2)
            val adapter = MovieListAdapter { movieData ->
                listener?.onMovieSelected(movieData)
            }
            this.adapter = adapter
            loadDataToAdapter(adapter)
        }
    }

    private fun loadDataToAdapter(adapter: MovieListAdapter) {
        lifecycleScope.launch {
            viewModel.movies.collect { movieList ->
                adapter.submitList(movieList)
            }
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface MovieListItemClickListener {
        fun onMovieSelected(movie: Movie)
    }

    companion object {
        fun create() = MovieListFragment()
    }
}