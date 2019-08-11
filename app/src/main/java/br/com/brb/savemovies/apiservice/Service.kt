package br.com.brb.savemovies.apiservice

import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.model.SearchDto
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {


    companion object {

        private var service: IService? = null
        private const val key = "af253783"
        private const val plot = "full"

        private fun getService(): IService {

            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.omdbapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                service = retrofit.create(IService::class.java)
            }

            return service!!
        }


        fun searchMovies(nameMovie: String, page: Int, listener: IResponse<MutableList<Movie>>) {
            getService().searchMovie(key, nameMovie, page).enqueue(object : Callback<SearchDto> {
                override fun onResponse(call: Call<SearchDto>, response: Response<SearchDto>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.response) {
                                listener.onResponseSuccess(it.search)
                            } else {
                                listener.onResponseError("")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<SearchDto>, t: Throwable) {
                    listener.onResponseError("")
                }
            })
        }


        fun searchMoviesPage(nameMovie: String, page: Int, listener: IResponse<MutableList<Movie>>) {
            getService().searchMovie(key, nameMovie, page).enqueue(object : Callback<SearchDto> {
                override fun onResponse(call: Call<SearchDto>, response: Response<SearchDto>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.response) {
                                listener.onResponseSuccess(it.search)
                            } else {
                                listener.onResponseError("")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<SearchDto>, t: Throwable) {
                    listener.onResponseError("")
                }
            })
        }

        fun getMovie(id: String, listener: IResponse<Movie>) {
            getService().getMovie(key, id, plot).enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        listener.onResponseSuccess(response.body()!!)
                    } else {
                        listener.onResponseError("")
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    listener.onResponseError("")
                }
            })
        }
    }
}