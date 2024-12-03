package com.example.moviesinfo.data

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("imdbID") val id: String,
    @SerializedName ("Title") val name: String,
    @SerializedName ("Poster") val imageURL: String,
    @SerializedName ("Year") val year: String,
    @SerializedName ("Plot") val synopsis: String,
    @SerializedName ("Runtime") val duration: String,
    @SerializedName ("Genre") val genre: String,
    @SerializedName ("Country") val country: String,
    @SerializedName ("Director") val director: String
)

data class MoviesResponse (
    @SerializedName ("Search") val search: List<MovieItem>,
    @SerializedName ("totalResults") val totalResults: String,
    @SerializedName ("Response") val response: String
)

data class MovieItem(
    @SerializedName ("Title") val itemTitle: String,
    @SerializedName ("Year") val itemYear: String,
    @SerializedName ("imdbID")  val imdbID: String,
    @SerializedName ("Type") val itemType: String,
    @SerializedName ("Poster") val itemPoster: String
)
