package br.com.brb.savemovies.view.detail

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.data.repository.MovieRepository


class DetailPresenter {

    var infoVisibility = ObservableInt(View.GONE)
    var loadingVisibility = ObservableInt(View.VISIBLE)
    var messageNoInternetVisibility = ObservableInt(View.GONE)

    val movieL = MutableLiveData<Movie>().apply { value = null }
    lateinit var imdbId: String


    fun getMovie( local: (Boolean) -> Unit) {
        MovieRepository.instance.getMovie(
            imdbId,
            { movie, salve ->
                movieL.value = movie
                changeView(View.VISIBLE, View.GONE, View.GONE)
                local(salve)
            },
            {
                changeView(View.GONE, View.GONE, View.VISIBLE)
            }
        )
    }

    fun saveOrDeleteMovie(save: (Boolean) -> Unit, delete: (Boolean) -> Unit) {
        movieL.value?.let { movie ->
            MovieRepository.instance.getLocalMovie(
                movie,
                {
                    save(it)
                },
                {
                    delete(it)
                }

            )
        }
    }


    private fun changeView(info: Int, loading: Int, internet: Int) {
        infoVisibility.set(info)
        loadingVisibility.set(loading)
        messageNoInternetVisibility.set(internet)
    }

}


