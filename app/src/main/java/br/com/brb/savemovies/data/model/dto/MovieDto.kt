package br.com.brb.savemovies.data.model.dto

import com.google.gson.annotations.SerializedName


class MovieDto {

    @SerializedName("Title")
    var title: String? = null

    @SerializedName("Year")
    var year: String? = null

    @SerializedName("Rated")
    var rated: String? = null

    @SerializedName("Released")
    var released: String? = null

    @SerializedName("Runtime")
    var runtime: String? = null

    @SerializedName("Genre")
    var genre: String? = null

    @SerializedName("Director")
    var director: String? = null

    @SerializedName("Writer")
    var writer: String? = null

    @SerializedName("Actors")
    var actors: String? = null

    @SerializedName("Production")
    var production: String? = null

    @SerializedName("Plot")
    var plot: String? = null

    @SerializedName("Language")
    var language: String? = null

    @SerializedName("Country")
    var country: String? = null

    @SerializedName("Awards")
    var awards: String? = null

    @SerializedName("Poster")
    var poster: String? = null

    var localPoster: String? = null

    var imdbRating: String? = null

    var imdbID: String? = null

    @SerializedName("Type")
    var type: String? = null

    @SerializedName("BoxOffice")
    var boxOffice: String? = null

    var response: String? = null

}