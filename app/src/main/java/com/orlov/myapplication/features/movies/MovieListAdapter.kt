package com.orlov.myapplication.features.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.orlov.myapplication.R
import com.orlov.myapplication.model.Movie

class MovieListAdapter(private val onClickCard: (item: Movie) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickCard)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.movie_title)
        private var rating = itemView.findViewById<RatingBar>(R.id.ratingView)
        private var picture: ImageView = itemView.findViewById(R.id.movie_image)
        private var ageRating = itemView.findViewById<TextView>(R.id.age_rating)
        private var numberOfReviews = itemView.findViewById<TextView>(R.id.reviews_number_text_view)
        private var genres = itemView.findViewById<TextView>(R.id.genreTextView)
        private var duration = itemView.findViewById<TextView>(R.id.movie_duration)
        private var favourite = itemView.findViewById<ImageView>(R.id.favourite_image_view)

        fun bind(movie: Movie, onClickCard: (item: Movie) -> Unit) {
            picture.load(movie.imageUrl)

            title.text = movie.title
            rating.rating = movie.rating/2
            ageRating.text = "${movie.pgAge}+"
            numberOfReviews.text = "${movie.reviewCount} Reviews"
            genres.text = movie.genres.joinToString { it.name }
            duration.text = "${movie.duration} MIN"
            if (movie.favourite) {
                favourite.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.pinky_red),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
            itemView.setOnClickListener {
                onClickCard(movie)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}