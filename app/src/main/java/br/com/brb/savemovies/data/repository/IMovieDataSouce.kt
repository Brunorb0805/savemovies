package br.com.brb.savemovies.data.repository

import br.com.brb.savemovies.data.model.entity.Movie


interface IMovieDataSouce {

    interface Remote {
        fun model(key: String, success: (Movie, Boolean) -> Unit, failure: () -> Unit)

        fun list(success: (List<Movie>) -> Unit, failure: () -> Unit)

        fun search(success: (List<Movie>) -> Unit, failure: () -> Unit)
    }

    interface Local {
        fun model(key: String, success: (Movie) -> Unit, failure: () -> Unit)

        fun save(movie: Movie, save: (Boolean) -> Unit)

        fun delete(movie: Movie, delete: (Boolean) -> Unit)
    }


    fun save(movie: Movie)

    fun delete(movie: Movie)

}
