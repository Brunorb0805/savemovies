package br.com.brb.savemovies.view.home

import br.com.brb.savemovies.model.Movie

interface IHomeView {

    fun callbackSuccessGetMovie(listMovies: List<Movie>)
    fun callbackSuccessEmptyGetMovie()

}