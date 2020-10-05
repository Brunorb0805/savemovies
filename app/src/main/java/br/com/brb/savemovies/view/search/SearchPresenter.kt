package br.com.brb.savemovies.view.search

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import br.com.brb.savemovies.apiservice.IResponse
import br.com.brb.savemovies.apiservice.Service
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.util.ConvertData
import br.com.brb.savemovies.util.VerifyInternet


class SearchPresenter(private val context: Context, private var view: ISearchContract.View?) :
    ISearchContract.Presenter {


    private var verifyInternet: VerifyInternet = VerifyInternet()

    var listAdapter: SearchMovieListAdapter? = null

    var loadingVisibility = ObservableInt(View.GONE)
    var messageNotFoundVisibility = ObservableInt(View.VISIBLE)
    var messageNotInternetVisibility = ObservableInt(View.GONE)

    private var page = 1
    private var searchText = ""


    init {
        listAdapter = SearchMovieListAdapter(this, mutableListOf())
    }


    override fun onDestroy() {
        view = null
    }


    fun searchMovies(text: String?, increment: Int) {
        text?.let { searchText = it }
        if (increment > 0) page += increment else page = 1

        if (searchText.length < 3) return

        if (verifyInternet.verifyConnection(context)) {
            val newData = page == 1
            changeView(View.VISIBLE, View.GONE, View.GONE)
            Service.searchMovies(
                ConvertData.generateDataRequest(searchText),
                page,
                object : IResponse<MutableList<Movie>> {
                    override fun onResponseSuccess(response: MutableList<Movie>?) {
                        response?.let { listAdapter?.setItems(it, newData) }
                        changeView(View.GONE, View.GONE, View.GONE)
                    }

                    override fun onResponseError(message: String?) {
                        if (newData) {
                            listAdapter?.setItems(mutableListOf(), newData)
                            changeView(View.GONE, View.VISIBLE, View.GONE)

                        } else {
                            changeView(View.GONE, View.GONE, View.GONE)
                        }
                    }
                })

        } else {
            listAdapter?.setItems(mutableListOf(), false)
            changeView(View.GONE, View.GONE, View.VISIBLE)
        }
    }

    fun onItemClick(model: Movie) {
        view?.itemClick(model)
    }


    private fun changeView(loading: Int, notFound: Int, internet: Int) {
        loadingVisibility.set(loading)
        messageNotFoundVisibility.set(notFound)
        messageNotInternetVisibility.set(internet)
    }

}
