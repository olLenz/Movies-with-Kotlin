package com.lenz.oliver.movieswithkotlin.ui.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Cast
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.utils.getPosterUrl

private enum class TYPE(val value: Int) {
    HEADER(1), CAST(2), DETAILS(3)
}

private const val POSITION_DETAILS = 0
private const val POSITION_CAST = 1

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
            TYPE.CAST.value -> {
                val cast = movie?.credits?.cast
                val size = cast?.size
                if (size != null && position < size && holder is CastViewHolder) {
                    holder.bind(cast.get(position))
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


        fun bind(movie: Movie) {

//            detailsDescriptionContainer.setOnClickListener({
//                if (detailsDescriptionMoreTv.visibility == View.GONE) {
//                    detailsDescriptionMoreTv.visibility = View.VISIBLE
//                    detailsDescriptionTv.maxLines = 5
//                } else {
//                    detailsDescriptionTv.maxLines = 1000
//                    detailsDescriptionMoreTv.visibility = View.GONE
//                }
//            })
        }
    }

    class CastViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.posterIv)
        private val castNameTv: TextView = itemView.findViewById(R.id.castNameTv)
        private val castCharacterTv: TextView = itemView.findViewById(R.id.castCharacterTv)

        fun bind(cast: Cast?) {
            if (cast == null) {
                return
            }

            imageView.loadImage(getPosterUrl(cast.imagePath), circleCrop = true)

            castNameTv.text = cast.name
            castCharacterTv.text = cast.character
        }

    }
}