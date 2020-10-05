package br.com.brb.savemovies.apiservice

import br.com.brb.savemovies.data.model.dto.MovieDto
import br.com.brb.savemovies.data.model.dto.SearchDto
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
    ): Call<MovieDto>

    @GET("?")
    fun getMovieL(
        @Query("apikey") key: String,
        @Query("i") id: String,
        @Query("plot") plot: String
    ): Call<MovieDto>
}