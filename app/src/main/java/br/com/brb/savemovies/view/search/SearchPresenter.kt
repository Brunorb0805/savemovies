package br.com.brb.savemovies.view.search

import android.content.Context
import br.com.brb.savemovies.apiservice.IResponse
import br.com.brb.savemovies.apiservice.Service
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.util.ConvertData
import br.com.brb.savemovies.util.VerifyInternet


class SearchPresenter(private val context: Context, private val view: ISearchView) {

    val movieList: MutableList<Movie> = mutableListOf()

    private var verifyInternet: VerifyInternet = VerifyInternet()
    

    fun searchMovies(name: String, page: Int) {
        if (verifyInternet.verifyConnection(context)) {
            view.showLoading()
            if (page == 1) {
                Service.searchMovies(
                    ConvertData.generateDataRequest(name),
                    page,
                    object : IResponse<MutableList<Movie>> {
                        override fun onResponseSuccess(response: MutableList<Movie>?) {
                            movieList.clear()
                            response?.let { movieList.addAll(it) }
                            view.callbackSuccessSearchMovies()
                        }

                        override fun onResponseError(message: String?) {
                            movieList.clear()
                            view.callbackErrorSearchMovies(message)
                        }
                    })
            } else {
                Service.searchMoviesPage(
                    ConvertData.generateDataRequest(name),
                    page,
                    object : IResponse<MutableList<Movie>> {
                        override fun onResponseSuccess(response: MutableList<Movie>?) {
                           response?.let {  movieList.addAll(response) }
                            view.callbackSuccessSearchMoviesPage()
                        }

                        override fun onResponseError(message: String?) {
                            view.callbackErrorSearchMoviesPage(message)
                        }
                    })
            }
        } else {
            movieList.clear()
            view.callbackNoInternet()
        }
    }
}