package br.com.brb.savemovies.view.home

import android.content.Context
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.persistence.MovieDatabase
import android.os.AsyncTask



class HomePresenter(private val context: Context, private val view: IHomeView) {

    val listMovie: MutableList<Movie> = mutableListOf()

    fun selectMovies() {
        SelectAsyncTask(this).execute()
    }

    private fun getFavoritesMovies() {

        val movies: MutableList<Movie>? = MovieDatabase.getInstance(context)?.movieDao()?.getAllMovies()

        movies?.let {
            listMovie.clear()

            if (it.isEmpty()) {
                view.callbackSuccessEmptyGetMovie()
            } else {
                listMovie.addAll(it)
                view.callbackSuccessGetMovie(listMovie)
            }
        }
    }

    private class SelectAsyncTask internal constructor(
        private val presenter: HomePresenter) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            presenter.getFavoritesMovies()
            return null
        }

    }
}
