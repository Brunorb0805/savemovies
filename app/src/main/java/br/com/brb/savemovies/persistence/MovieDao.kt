package br.com.brb.savemovies.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.brb.savemovies.model.Movie

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Query("SELECT * from Movie")
    fun getAllMovies(): MutableList<Movie>

    @Query("SELECT * from Movie where imdbID = :imdb")
    fun getMovie(imdb: String): Movie

    @Query("DELETE from Movie where imdbID = :imdb")
    fun delete(imdb: String)

    @Delete
    fun delete(movie: Movie)
}