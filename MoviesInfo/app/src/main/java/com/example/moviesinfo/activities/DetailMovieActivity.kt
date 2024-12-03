package com.example.moviesinfo.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviesinfo.R
import com.example.moviesinfo.data.Movie
import com.example.moviesinfo.databinding.ActivityDetailMovieBinding
import com.example.moviesinfo.utils.RetrofitManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieActivity : AppCompatActivity() {
    private val service = RetrofitManager.getRetrofit()
    private lateinit var movie: Movie
    companion object {
        const val PARAM_IMDB_ID = "MOVIE_ID"
    }
    private lateinit var binding: ActivityDetailMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra(PARAM_IMDB_ID)!!

        getMovie(id)
    }

    private fun loadData() {
        println(movie)
        supportActionBar?.title = movie.name + " (${movie.year})"
        Picasso.get().load(movie.imageURL).into(binding.cardViewMoviePoster)
        binding.cardViewTitleAndYear.text = buildString {
        append(movie.name)
        append(" (")
        append(movie.year)
        append(")")
        }
        binding.cardViewSynopsis.text = movie.synopsis
        binding.countryText.text = movie.country
        binding.directorText.text = buildString {
        append("Directed by ")
        append(movie.director)
    }
        binding.genreText.text = buildString {
        append("Genre: ")
        append(movie.genre)
    }
        binding.runtimeText.text = buildString {
        append("Runtime: ")
        append(movie.duration)
    }
    }

    private fun getMovie(id: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                movie = service.getMovieById(id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }
}
