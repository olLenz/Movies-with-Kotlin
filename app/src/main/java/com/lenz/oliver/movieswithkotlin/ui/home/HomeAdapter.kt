package com.lenz.oliver.movieswithkotlin.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.utils.getPosterUrl

class HomeAdapter(private val inflater: LayoutInflater,
                  private val onInteractionListener: OnInteractionListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HomeViewHolder(
                    inflater.inflate(R.layout.card_home, parent, false),
                    onInteractionListener
            )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
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

    class HomeViewHolder(itemView: View, private val onInteractionListener: OnInteractionListener)
        : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.posterIv)

        fun bind(movie: Movie) {
            imageView.apply {
                loadImage(getPosterUrl(movie.posterPath))

                setOnClickListener({
                    this.transitionName = context.getString(R.string.transition_name_recommendation)
                    onInteractionListener.onItemClicked(movie, imageView)
                })
            }

        }

    }
}