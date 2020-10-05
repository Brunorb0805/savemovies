package br.com.brb.savemovies.data.repository

import br.com.brb.savemovies.apiservice.Service
import br.com.brb.savemovies.data.datalocal.MovieDatabase
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.util.AppExecutors


class MovieRepository(
    private val movieRemoteDataSource: IMovieDataSouce.Remote,
    private val movieLocalDataSource: IMovieDataSouce.Local
) {

    companion object {
        private var mInstance: MovieRepository? = null

        @get:Synchronized
        val instance: MovieRepository
            get() {
                synchronized(MovieRepository::class.java) {
                    if (mInstance == null) {
                        mInstance = MovieRepository(
                            MovieRemoteDataSource(Service.instance),
                            MovieLocalDataSource(MovieDatabase.instance.movieDao(), AppExecutors())
                        )
                    }

                    return mInstance!!
                }
            }
    }

    fun getMovie(key: String, success: (Movie, Boolean) -> Unit, failure: () -> Unit) {
        movieLocalDataSource.model(
            key,
            { model -> success(model, true) },
            { movieRemoteDataSource.model(key, success, failure) }
        )
    }

    fun getLocalMovie(model: Movie, save: (Boolean) -> Unit, delete: (Boolean) -> Unit) {
        movieLocalDataSource.model(
            model.imdbID!!,
            { movie -> deleteMovie(movie, delete) },
            { saveMovie(model, save) }
        )
    }

    private fun deleteMovie(movie: Movie, delete: (Boolean) -> Unit) {
        movieLocalDataSource.delete(
            movie,
        ) { bool -> delete(bool) }
    }

    private fun saveMovie(movie: Movie, save: (Boolean) -> Unit) {
        movieLocalDataSource.save(
            movie
        ) { bool -> save(bool) }
    }

    fun listMovies() {

    }

    fun searchMovies() {

    }

}
