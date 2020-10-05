package br.com.brb.savemovies.data.datalocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.brb.savemovies.application.AppApplication
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.data.repository.MovieRepository


@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var mIntance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (mIntance == null) {
                synchronized(MovieDatabase::class) {
                    mIntance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java, "movie.db"
                    ).build()
                }
            }
            return mIntance
        }

        @get:Synchronized
        val instance: MovieDatabase
            get() {
                if(mIntance == null) {
                    synchronized(MovieDatabase::class) {
                        mIntance = Room.databaseBuilder(
                            AppApplication().applicationContext,
                            MovieDatabase::class.java, "movie.db"
                        ).build()
                    }
                }

                return mIntance!!
            }
    }
}