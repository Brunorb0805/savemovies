package br.com.brb.savemovies.data.model.entity

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.brb.savemovies.data.model.dto.MovieDto
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.Serializable


@Entity
class Movie : Serializable {

    companion object {
        @JvmStatic
        @BindingAdapter("posterRounded")
        fun loadRoundedImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null && imageUrl.isNotEmpty() && imageUrl != "N/A") {
                Glide.with(view.context)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("poster")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null && imageUrl.isNotEmpty() && imageUrl != "N/A") {
                Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
            }
        }

        fun map(dto: MovieDto?) : Movie {
            return Movie().apply {
                title = dto?.title
                year = dto?.year
                rated = dto?.rated
                released = dto?.released
                runtime = dto?.runtime
                director = dto?.director
                writer = dto?.writer
                actors = dto?.actors
                production = dto?.production
                plot = dto?.plot
                language = dto?.language
                country = dto?.country
                awards = dto?.awards
                poster = dto?.poster
                imdbRating = dto?.imdbRating
                imdbID = dto?.imdbID
                type = dto?.type
                boxOffice = dto?.boxOffice

            }
        }
    }

    var title: String? = null

    var year: String? = null

    var rated: String? = null

    var released: String? = null

    var runtime: String? = null

    var genre: String? = null

    var director: String? = null

    var writer: String? = null

    var actors: String? = null

    var production: String? = null

    var plot: String? = null

    var language: String? = null

    var country: String? = null

    var awards: String? = null

    var poster: String? = null

    var localPoster: String? = null

    var imdbRating: String? = null

    @NonNull
    @PrimaryKey
    var imdbID: String? = null

    var type: String? = null

    var boxOffice: String? = null

}
