package com.example.moviesinfo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinfo.data.MovieItem
import com.example.moviesinfo.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private var items: List<MovieItem>, val onItemClick: (MovieItem) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = items[position]
        holder.init(movieItem)
        holder.itemView.setOnClickListener {
            onItemClick(movieItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<MovieItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun init(movie: MovieItem) {
        binding.movieName.text = movie.itemTitle
        binding.movieYear.text = movie.itemYear
        Picasso.get().load(movie.itemPoster).into(binding.movieImageView)
    }
}

