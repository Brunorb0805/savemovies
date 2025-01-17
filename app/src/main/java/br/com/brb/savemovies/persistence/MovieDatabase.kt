package br.com.brb.savemovies.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.brb.savemovies.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java, "car.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}