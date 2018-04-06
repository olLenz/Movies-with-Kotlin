package com.lenz.oliver.movieswithkotlin.ui.recommendations

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.repository.models.Movie

class RecommendationAdapter(private val inflater: LayoutInflater)
    : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    private var items = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecommendationViewHolder(inflater.inflate(R.layout.card_recommendation, parent, false))


    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(movies: List<Movie>) {
        this.items = movies
        notifyDataSetChanged()
    }

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {

        }

    }
}