package com.example.moviesinfo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesinfo.R
import com.example.moviesinfo.adapters.MovieAdapter
import com.example.moviesinfo.data.MovieItem
import com.example.moviesinfo.databinding.ActivityMainBinding
import com.example.moviesinfo.utils.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListActivity : AppCompatActivity() {
    private val service = RetrofitManager.getRetrofit()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private var movieList: List<MovieItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MovieAdapter(movieList) { movieItem ->
            navigateToDetail(movieItem)
        }
        binding.recyclerViewID.adapter = adapter
        binding.recyclerViewID.layoutManager = GridLayoutManager(this, 3)

        searchMovieByName("Harry")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu_options, menu)

        val searchItemView = menu.findItem(R.id.searchBarView)
        val searchView = searchItemView.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMovieByName(query.toString())
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return true
    }
    private fun navigateToDetail(movie: MovieItem) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.PARAM_IMDB_ID, movie.imdbID)
        startActivity(intent)
    }

    private fun searchMovieByName(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.getMoviesByName(query)

                CoroutineScope(Dispatchers.Main).launch {
                    if (result != null) {
                        if (result.response != "False") {
                            adapter.updateItems(result.search)
                        } else {
                            println("Nothing to show")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }
}
