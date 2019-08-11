package br.com.brb.savemovies.model

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
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
    }

    @SerializedName("Title")
    var title: String? = null

    @SerializedName("Year")
    var year: String? = null

    @SerializedName("Rated")
    var rated: String? = null

    @SerializedName("Released")
    var released: String? = null

    @SerializedName("Runtime")
    var runtime: String? = null

    @SerializedName("Genre")
    var genre: String? = null

    @SerializedName("Director")
    var director: String? = null

    @SerializedName("Writer")
    var writer: String? = null

    @SerializedName("Actors")
    var actors: String? = null

    @SerializedName("Production")
    var production: String? = null

    @SerializedName("Plot")
    var plot: String? = null

    @SerializedName("Language")
    var language: String? = null

    @SerializedName("Country")
    var country: String? = null

    @SerializedName("Awards")
    var awards: String? = null

    @SerializedName("Poster")
    var poster: String? = null

    var localPoster: String? = null

    var imdbRating: String? = null

    @NonNull
    @PrimaryKey
    var imdbID: String? = null

    @SerializedName("Type")
    var type: String? = null

    @SerializedName("BoxOffice")
    var boxOffice: String? = null

    var response: String? = null

}