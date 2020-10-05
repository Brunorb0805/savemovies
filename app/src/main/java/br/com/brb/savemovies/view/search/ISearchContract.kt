package br.com.brb.savemovies.view.search

import br.com.brb.savemovies.data.model.entity.Movie


interface ISearchContract {

    interface View {
//        fun callbackSuccessSearchMovies()
//        fun callbackErrorSearchMovies(response: String?)
//        fun callbackSuccessSearchMoviesPage()
//        fun callbackErrorSearchMoviesPage(response: String?)
//        fun callbackNoInternet()

        fun itemClick(model: Movie)
    }


    interface Presenter {
        fun onDestroy()
    }
}