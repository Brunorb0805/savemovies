package br.com.brb.savemovies.view.search

interface ISearchView {

    fun callbackSuccessSearchMovies()
    fun callbackErrorSearchMovies(response: String?)
    fun callbackSuccessSearchMoviesPage()
    fun callbackErrorSearchMoviesPage(response: String?)
    fun callbackNoInternet()

    fun showLoading()
    fun hideLoading()
}