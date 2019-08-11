package br.com.brb.savemovies.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ItemSearchMovieListBinding
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.view.detail.DetailsActivity

class SearchMovieListAdapter(private val context: SearchActivity,
                             private val listMovies: MutableList<Movie>) :
    RecyclerView.Adapter<SearchMovieListAdapter.ListSearchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchMovieListBinding>(inflater, R.layout.item_search_movie_list, parent, false)

        return ListSearchViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ListSearchViewHolder, position: Int) = holder.bind(listMovies[position])

    override fun getItemCount() = listMovies.size

    inner class ListSearchViewHolder(private val binding: ItemSearchMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                context.startActivity(DetailsActivity.newInstance(context, movie.imdbID!!))
                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out)
            }
        }
    }
}