package br.com.brb.savemovies.data.repository

import br.com.brb.savemovies.BuildConfig
import br.com.brb.savemovies.apiservice.IService
import br.com.brb.savemovies.data.model.dto.MovieDto
import br.com.brb.savemovies.data.model.entity.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRemoteDataSource(private val api: IService) : IMovieDataSouce.Remote {

    private val plot: String = "full"

    override fun list(success: (List<Movie>) -> Unit, failure: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun search(success: (List<Movie>) -> Unit, failure: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun model(key: String, success: (Movie, Boolean) -> Unit, failure: () -> Unit) {
        api.getMovie(BuildConfig.API_KEY, key, plot)
            .enqueue(object : Callback<MovieDto> {
                override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                    if (response.isSuccessful) {
                        success(Movie.map(response.body()), false)
                    } else {
                        failure()
                    }
                }

                override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                    failure()
                }
            })

    }

}
