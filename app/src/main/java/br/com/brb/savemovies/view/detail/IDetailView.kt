package br.com.brb.savemovies.view.detail

interface IDetailView {

    fun callbackSuccessGetMovie()
    fun callbackErrorGetMovie(response: String?)

    fun callbackSuccessGetLocalMovie()

    fun callbackSuccessDeleteMovie()
    fun callbackSuccessSaveMovie()

    fun callbackNoInternet()

    fun showLoading()
    fun hideLoading()
}