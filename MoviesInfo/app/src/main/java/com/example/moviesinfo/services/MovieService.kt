package com.example.moviesinfo.services

import com.example.moviesinfo.data.Movie
import com.example.moviesinfo.data.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET(".")
    suspend fun getMoviesByName(
        @Query("s") query: String,
        @Query("apiKey") apiKey: String="fb7aca4"
    ) : MoviesResponse

    @GET(".")
    suspend fun getMovieById(
        @Query("i") query: String,
        @Query("apiKey") apiKey: String="fb7aca4"
    ) : Movie
}
