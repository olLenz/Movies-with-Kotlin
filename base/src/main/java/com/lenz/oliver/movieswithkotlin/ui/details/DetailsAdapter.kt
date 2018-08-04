package com.lenz.oliver.movieswithkotlin.ui.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lenz.oliver.movieswithkotlin.base.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Cast
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.utils.getPosterUrl

private enum class TYPE(val value: Int) {
    CAST(1), DETAILS(2)
}

private const val POSITION_DETAILS = 0

class DetailsAdapter(private val inflater: LayoutInflater)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movie: Movie? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE.DETAILS.value -> {
                DetailsViewHolder(
                        inflater.inflate(R.layout.card_details, parent, false)
                )
            }
            else -> {
                CastViewHolder(
                        inflater.inflate(R.layout.card_cast, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE.DETAILS.value -> {
                (holder as? DetailsViewHolder)?.bind(movie)
            }
            TYPE.CAST.value -> {
                val cast = movie?.credits?.cast
                val size = cast?.size
                if (size != null && position < size && holder is CastViewHolder) {
                    holder.bind(cast[position])
                }
            }
        }
    }

    override fun getItemViewType(position: Int) =
            when (position) {
                POSITION_DETAILS -> TYPE.DETAILS.value
                else -> TYPE.CAST.value
            }

    override fun getItemCount(): Int {
        return if (movie == null) {
            0
        } else {
            movie?.credits?.cast?.size?.plus(1) ?: 1
        }
    }

    fun setMovie(movie: Movie?) {
        this.movie = movie
        notifyDataSetChanged()
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val detailsTitleTv: TextView = itemView.findViewById(R.id.detailsTitleTv)
        private val detailsGenresTv: TextView = itemView.findViewById(R.id.detailsGenresTv)
        private val detailsDescriptionTv: TextView = itemView.findViewById(R.id.detailsDescriptionTv)
        private val detailsDescriptionContainer: View = itemView.findViewById(R.id.detailsDescriptionContainer)
        private val detailsDescriptionMoreTv: TextView = itemView.findViewById(R.id.detailsDescriptionMoreTv)
        private val detailsReleaseTv: TextView = itemView.findViewById(R.id.detailsReleaseTv)
        private val detailsRatingTv: TextView = itemView.findViewById(R.id.detailsRatingTv)
        private val detailsRuntimeTv: TextView = itemView.findViewById(R.id.detailsRuntimeTv)

        fun bind(movie: Movie?) {
            if (movie == null) {
                return
            }

            detailsTitleTv.text = movie.title

            if (movie.genres?.isNotEmpty() == true) {
                detailsGenresTv.visibility = View.VISIBLE
                detailsGenresTv.text = movie.genres
                        .map { it.name }
                        .joinToString(separator = " | ")
            } else {
                detailsGenresTv.visibility = View.GONE
            }

            if (movie.overview?.isEmpty() == true) {
                detailsDescriptionContainer.visibility = View.GONE
            } else {
                detailsDescriptionContainer.visibility = View.VISIBLE
                detailsDescriptionTv.text = movie.overview
                detailsDescriptionContainer.setOnClickListener({
                    if (detailsDescriptionMoreTv.visibility == View.GONE) {
                        detailsDescriptionMoreTv.visibility = View.VISIBLE
                        detailsDescriptionTv.maxLines = 5
                    } else {
                        detailsDescriptionTv.maxLines = 1000
                        detailsDescriptionMoreTv.visibility = View.GONE
                    }
                })
            }

            movie.releaseDate?.let {
                // implement proper date handling instead of parsing the string here
                detailsReleaseTv.text = it
                        .split("-")
                        .reversed()
                        .joinToString(".")
            }

            movie.voteAverage?.let {
                detailsRatingTv.text = "${it} / 10"
            }

            movie.runtime?.let {
                detailsRuntimeTv.text = "${it} m"
            }
        }
    }

    class CastViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private val castPosterIv: ImageView = itemView.findViewById(R.id.castPosterIv)
        private val castNameTv: TextView = itemView.findViewById(R.id.castNameTv)
        private val castCharacterTv: TextView = itemView.findViewById(R.id.castCharacterTv)

        fun bind(cast: Cast?) {
            if (cast == null) {
                return
            }

            castPosterIv.loadImage(getPosterUrl(cast.imagePath), true)

            castNameTv.text = cast.name
            castCharacterTv.text = cast.character
        }

    }
}