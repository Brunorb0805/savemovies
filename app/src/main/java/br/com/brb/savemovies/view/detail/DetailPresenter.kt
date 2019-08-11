package br.com.brb.savemovies.view.detail

import android.content.Context
import android.os.AsyncTask
import br.com.brb.savemovies.apiservice.IResponse
import br.com.brb.savemovies.apiservice.Service
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.persistence.MovieDatabase
import br.com.brb.savemovies.util.VerifyInternet


class DetailPresenter(private val context: Context, private val view: IDetailView) {

    private var verifyInternet: VerifyInternet = VerifyInternet()

    var movie: Movie? = null
    lateinit var imdbId: String


    fun loadMovieInfo() {
        LoadMovieInfoAsyncTask(this).execute()
    }

    fun saveOrDeleteMovie() {
        movie?.let { SaveOrDeleteMovieAsyncTask(this).execute() }
    }


    private fun asyncLoadMovieInfo() {
        movie = MovieDatabase.getInstance(context)?.movieDao()?.getMovie(imdbId)

        movie?.let {
            view.callbackSuccessGetLocalMovie()
        } ?: getMovie(imdbId)
    }

    private fun asyncSaveOrDeleteMovie() {
        val localMovie = MovieDatabase.getInstance(context)?.movieDao()?.getMovie(imdbId)

        localMovie?.let {
            MovieDatabase.getInstance(context)?.movieDao()?.delete(it)

            view.callbackSuccessDeleteMovie()
        } ?: run {
            MovieDatabase.getInstance(context)?.movieDao()?.insert(movie!!)

            view.callbackSuccessSaveMovie()
        }
    }

    private fun getMovie(id: String) {
        if (verifyInternet.verifyConnection(context)) {
            view.showLoading()
            Service.getMovie(id, object : IResponse<Movie> {
                override fun onResponseSuccess(response: Movie?) {
                    response?.let {
                        movie = it
                        view.callbackSuccessGetMovie()
                    } ?: view.callbackErrorGetMovie("")
                }

                override fun onResponseError(message: String?) {
                    view.callbackErrorGetMovie(message)
                }
            })
        } else {
            view.callbackNoInternet()
        }
    }


    private class LoadMovieInfoAsyncTask internal constructor(private val presenter: DetailPresenter) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            presenter.asyncLoadMovieInfo()
            return null
        }
    }

    private class SaveOrDeleteMovieAsyncTask internal constructor(private val presenter: DetailPresenter) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            presenter.asyncSaveOrDeleteMovie()
            return null
        }
    }
}

