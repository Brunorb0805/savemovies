package br.com.brb.savemovies.data.datalocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.brb.savemovies.data.model.entity.Movie


@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @get:Query("SELECT * FROM Movie ORDER BY title ASC")
    val allMovies: LiveData<List<Movie>>

    @Query("SELECT * from Movie where imdbID = :imdb")
    fun getMovie(imdb: String): Movie?

    @Query("SELECT * from Movie where imdbID = :imdb")
    fun getMovieL(imdb: String): LiveData<Movie>


    @Query("DELETE from Movie where imdbID = :imdb")
    fun delete(imdb: String)

    @Delete
    fun delete(movie: Movie)

}
