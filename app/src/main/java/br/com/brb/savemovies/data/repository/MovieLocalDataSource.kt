package br.com.brb.savemovies.data.repository

import br.com.brb.savemovies.data.datalocal.MovieDao
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.util.AppExecutors


class MovieLocalDataSource(private val dao: MovieDao, private val appExecutors: AppExecutors) :
    IMovieDataSouce.Local {

    override fun model(key: String, success: (Movie) -> Unit, failure: () -> Unit) {
        appExecutors.roomThreadExecutor.execute {
            val breeds = dao.getMovie(key)
            appExecutors.mainThreadExecutor.execute {
                breeds?.let {
                    success(breeds)
                } ?: failure()
            }
        }
    }

    override fun save(movie: Movie, save: (Boolean) -> Unit) {
        appExecutors.roomThreadExecutor.execute {
            try {
                dao.insert(movie)
                appExecutors.mainThreadExecutor.execute { save(true) }
            } catch (e: Exception) {
                appExecutors.mainThreadExecutor.execute { save(false) }
            }
        }
    }

    override fun delete(movie: Movie, delete: (Boolean) -> Unit) {
        appExecutors.roomThreadExecutor.execute {
            try {
                dao.delete(movie)
                appExecutors.mainThreadExecutor.execute { delete(true) }
            } catch (e: Exception) {
                appExecutors.mainThreadExecutor.execute { delete(false) }
            }
        }
    }

}
