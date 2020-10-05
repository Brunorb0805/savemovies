package br.com.brb.savemovies.view.home

import br.com.brb.savemovies.data.model.entity.Movie

interface IHomeContract {

    interface View {
        fun onItemClick(model: Movie)
    }

    interface Presenter {
        fun onDestroy()
    }

}
