package br.com.brb.savemovies.application

import android.app.Application
import android.content.Context


class AppApplication : Application() {

    companion object {
        const val MOVIE_LIST_KEY = "movie_list"

        private var instance: AppApplication? = null
//        var retrofit = RetrofitConfig().configureRetrofit()

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
//        Hawk.init(applicationContext).build()
    }


}