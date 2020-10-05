package br.com.brb.savemovies.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ItemSearchMovieListBinding
import br.com.brb.savemovies.data.model.entity.Movie


class SearchMovieListAdapter(
    private val presenter: SearchPresenter,
    private val listMovies: MutableList<Movie>
) :
    RecyclerView.Adapter<SearchMovieListAdapter.ListSearchViewHolder>() {


//    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
//        val layoutInflater = LayoutInflater.from(viewGroup.context)
//        val binding =
//            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, position, viewGroup, false)
//
//        return RecyclerView.ViewHolder(binding)
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchMovieListBinding>(
            inflater,
            R.layout.item_search_movie_list,
            parent,
            false
        )

        return ListSearchViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ListSearchViewHolder, position: Int) =
        holder.bind(listMovies[position])

    override fun getItemCount() = listMovies.size


    fun setItems(list: MutableList<Movie>, newData: Boolean) {
        if (newData) listMovies.clear()

        listMovies.addAll(list)
        notifyDataSetChanged()
    }


    inner class ListSearchViewHolder(private val binding: ItemSearchMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.model = movie
            binding.presenter = presenter
            binding.executePendingBindings()

//            binding.root.setOnClickListener {
//                context.startActivity(DetailsActivity.newInstance(context, movie.imdbID!!))
//                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out)
//            }
        }
    }
}