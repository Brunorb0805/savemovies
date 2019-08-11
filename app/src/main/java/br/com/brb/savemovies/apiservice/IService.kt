package br.com.brb.savemovies.apiservice

import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.model.SearchDto
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {

    @GET("?")
    fun searchMovie(
        @Query("apikey") key: String,
        @Query("s") name: String,
        @Query("page") page: Int
    ): Call<SearchDto>


    @GET("?")
    fun getMovie(
        @Query("apikey") key: String,
        @Query("i") id: String,
        @Query("plot") plot: String
    ): Call<Movie>
}