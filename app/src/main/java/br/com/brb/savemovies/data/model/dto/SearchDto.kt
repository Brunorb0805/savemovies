package br.com.brb.savemovies.data.model.dto

import com.google.gson.annotations.SerializedName


class SearchDto {

    @SerializedName("Search")
    var search: MutableList<MovieDto>? = null

    @SerializedName("Response")
    var response: Boolean = false

    @SerializedName("Error")
    var error: String? = null

}
