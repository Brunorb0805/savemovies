package br.com.brb.savemovies.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ItemHomeMovieListBinding
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.view.detail.DetailsActivity
import java.util.*

class HomeListAdapter(private val activity: HomeActivity, private val listMovies: MutableList<Movie>) :
    RecyclerView.Adapter<HomeListAdapter.ListHomeViewHolder>(), Filterable {


    private var filteredListMovies: MutableList<Movie> = listMovies

    private var movieFilter: MoviesFilter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemHomeMovieListBinding>(inflater, R.layout.item_home_movie_list, parent, false)

        return ListHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHomeViewHolder, position: Int) = holder.bind(filteredListMovies[position])

    override fun getItemCount() = filteredListMovies.size

    override fun getFilter(): Filter {
        if (movieFilter == null) movieFilter = MoviesFilter()

        return movieFilter!!
    }


//    inner class ListHomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        val binding: ItemHomeMovieListBinding? = DataBindingUtil.bind(view)
//
//    }

    inner class ListHomeViewHolder(private val binding: ItemHomeMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                activity.startActivity(DetailsActivity.newInstance(activity, movie.imdbID!!))
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out)
            }
        }
    }

    inner class MoviesFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint != null && constraint.isNotEmpty()) {
                val tempList = ArrayList<Movie>()

                // search content in friend list
                for (movie in listMovies) {
                    movie.title?.let {
                        if (it.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(movie)
                        }
                    }
                }

                filterResults.count = tempList.size

                filterResults.values = tempList
            } else {
                filterResults.count = listMovies.size
                filterResults.values = listMovies
            }

            return filterResults
        }


        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredListMovies = results.values as MutableList<Movie>
            notifyDataSetChanged()
        }
    }

}