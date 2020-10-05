package br.com.brb.savemovies.view.home

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.data.datalocal.MovieDatabase


class HomePresenter(private val context: Context, private var view: IHomeContract.View?) : IHomeContract.Presenter {

    var listAdapter: HomeListAdapter? = null

    var listMovieLive: LiveData<List<Movie>>?

    var messageNotFoundVisibility = ObservableInt(View.VISIBLE)
    var recyclerVisibility = ObservableInt(View.GONE)


    override fun onDestroy() {
        view = null
    }

    init {
        listAdapter = HomeListAdapter(this, mutableListOf())
        listMovieLive = MovieDatabase.getInstance(context)?.movieDao()?.allMovies
    }


    fun selectMovies() {
        listMovieLive = MovieDatabase.getInstance(context)?.movieDao()?.allMovies
    }

    fun filter(filterText: String) {
        listAdapter?.filter?.filter(filterText)
    }

    fun setItems(list: List<Movie>) {
        if (list.isEmpty()) {
            changeView(View.GONE, View.VISIBLE)

        } else {
            changeView(View.VISIBLE, View.GONE)
            listAdapter?.setItems(list)
        }
    }

    fun onItemClick(model: Movie) {
        view?.onItemClick(model)
    }


    private fun changeView(recycler: Int, notFound: Int) {
        recyclerVisibility.set(recycler)
        messageNotFoundVisibility.set(notFound)
    }

}
