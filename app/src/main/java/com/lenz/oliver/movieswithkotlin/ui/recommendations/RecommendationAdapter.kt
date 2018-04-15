package com.lenz.oliver.movieswithkotlin.ui.recommendations

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.utils.getBackdropUrl

class RecommendationAdapter(private val inflater: LayoutInflater,
                            private val onInteractionListener: OnInteractionListener)
    : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecommendationViewHolder(
                    inflater.inflate(R.layout.card_recommendation, parent, false),
                    onInteractionListener
            )


    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    interface OnInteractionListener {

        fun onItemClicked(movie: Movie, sharedElement: View)

    }

    class RecommendationViewHolder(itemView: View,
                                   private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val recommendationMovieIv = itemView.findViewById<ImageView>(R.id.recommendationMovieIv)

        fun bind(movie: Movie) {
            itemView.setOnClickListener({
                onInteractionListener.onItemClicked(movie, recommendationMovieIv)
            })

            recommendationMovieIv.apply {
                loadImage(getBackdropUrl(movie.backdropPath))
                transitionName = context.getString(R.string.transition_name_recommendation) + movie.id
            }
        }

    }
}