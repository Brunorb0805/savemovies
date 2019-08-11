package br.com.brb.savemovies.model

import com.google.gson.annotations.SerializedName

class SearchDto {

    @SerializedName("Search")
    var search: MutableList<Movie>? = null

    @SerializedName("Response")
    var response: Boolean = false

    @SerializedName("Error")
    var error: String? = null


}