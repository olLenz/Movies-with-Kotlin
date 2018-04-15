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
import com.lenz.oliver.movieswithkotlin.utils.getPosterUrl

class CastAdapter(private val inflater: LayoutInflater)
    : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private var cast = listOf<Cast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CastViewHolder(
                    inflater.inflate(R.layout.card_cast, parent, false)
            )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    override fun getItemCount() = cast.size

    fun setCast(cast: List<Cast>) {
        this.cast = cast
        notifyDataSetChanged()
    }

    class CastViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.posterIv)
        private val castNameTv: TextView = itemView.findViewById(R.id.castNameTv)
        private val castCharacterTv: TextView = itemView.findViewById(R.id.castCharacterTv)

        fun bind(cast: Cast) {
            imageView.loadImage(getPosterUrl(cast.imagePath))

            castNameTv.text = cast.name
            castCharacterTv.text = cast.character
        }

    }
}