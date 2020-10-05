package br.com.brb.savemovies.apiservice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.brb.savemovies.BuildConfig
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.data.model.dto.MovieDto
import br.com.brb.savemovies.data.model.dto.SearchDto
import br.com.brb.savemovies.data.repository.MovieLocalDataSource
import br.com.brb.savemovies.data.repository.MovieRemoteDataSource
import br.com.brb.savemovies.data.repository.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Service {

    companion object {

        private var mService: IService? = null
        private var service: IService? = null
        private const val plot = "full"

        private fun getService(): IService {

            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                service = retrofit.create(IService::class.java)
            }

            return service!!
        }

        @get:Synchronized
        val instance: IService
            get() {
                synchronized(MovieRepository::class.java) {
                    if (mService == null) {
                        val retrofit = Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        mService = retrofit.create(IService::class.java)
                    }

                    return mService!!
                }
            }


        fun searchMovies(nameMovie: String, page: Int, listener: IResponse<MutableList<Movie>>) {
            getService().searchMovie(BuildConfig.API_KEY, nameMovie, page)
                .enqueue(object : Callback<SearchDto> {
                    override fun onResponse(call: Call<SearchDto>, response: Response<SearchDto>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                if (it.response) {
                                    val movies = mutableListOf<Movie>()

                                    it.search?.forEach { dto -> movies.add(Movie.map(dto)) }

                                    listener.onResponseSuccess(movies)
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
            getService().getMovie(BuildConfig.API_KEY, id, plot)
                .enqueue(object : Callback<MovieDto> {
                    override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                        if (response.isSuccessful) {
                            listener.onResponseSuccess(Movie.map(response.body()))
                        } else {
                            listener.onResponseError("")
                        }
                    }

                    override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                        listener.onResponseError("")
                    }
                })
        }

        fun getMovieL(id: String, listener: IResponse<LiveData<Movie>>) {
            getService().getMovieL(BuildConfig.API_KEY, id, plot)
                .enqueue(object : Callback<MovieDto> {
                    override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                        if (response.isSuccessful) {
                            listener.onResponseSuccess(
                                MutableLiveData<Movie>().apply {
                                    value = Movie.map(response.body())
                                })
                        } else {
                            listener.onResponseError("")
                        }
                    }

                    override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                        listener.onResponseError("")
                    }
                })
        }
    }
}
