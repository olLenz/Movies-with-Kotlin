package com.lenz.oliver.movieswithkotlin.ui.recommendations

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.utils.getBackdropUrl

private enum class TYPE(val value: Int) {
    NORMAL(2), HEADER(1)
}

private const val POSITTION_HEADER = 0

class RecommendationAdapter(private val inflater: LayoutInflater,
                            private val onInteractionListener: OnInteractionListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE.HEADER.value -> {
                HeaderViewHolder(
                        inflater.inflate(R.layout.card_header, parent, false)
                )
            }
            else -> {
                RecommendationViewHolder(
                        inflater.inflate(R.layout.card_recommendation, parent, false),
                        onInteractionListener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE.NORMAL.value -> (holder as RecommendationViewHolder).bind(movies[position - 1])
        }
    }

    override fun getItemViewType(position: Int) =
            when (position) {
                POSITTION_HEADER -> TYPE.HEADER.value
                else -> TYPE.NORMAL.value
            }

    override fun getItemCount() = movies.size + 1

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    interface OnInteractionListener {

        fun onItemClicked(movie: Movie, sharedElement: View)

    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class RecommendationViewHolder(itemView: View,
                                   private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val recommendationMovieIv = itemView.findViewById<ImageView>(R.id.recommendationMovieIv)
        private val recommendationTitleTv = itemView.findViewById<TextView>(R.id.recommendationTitleTv)
        private val recommendationSubtitleTv = itemView.findViewById<TextView>(R.id.recommendationSubtitleTv)

        fun bind(movie: Movie) {
            itemView.setOnClickListener({
                onInteractionListener.onItemClicked(movie, recommendationMovieIv)
            })

            recommendationMovieIv.apply {
                loadImage(getBackdropUrl(movie.backdropPath))
                transitionName = context.getString(R.string.transition_name_details) + movie.id
            }

            recommendationTitleTv.text = movie.title
            recommendationSubtitleTv.text = "${movie.voteAverage} von 10"
        }

    }
}