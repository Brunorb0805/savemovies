package br.com.brb.savemovies.view.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brb.savemovies.R
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener, IHomeView {

    //region Atributes
    private lateinit var listAdapter: HomeListAdapter

    private var presenter: HomePresenter? = null
    //endregion


    companion object {

        var isNewItem = false

        fun newInstance(context: Context) : Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }


    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter = HomePresenter(this, this)

        init()
    }

    override fun onResume() {
        super.onResume()
        if (isNewItem) {
            presenter?.selectMovies()
            isNewItem = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(this)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            goSearchActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun callbackSuccessGetMovie(listMovies: List<Movie>) {
        runOnUiThread {
            listAdapter.notifyDataSetChanged()
            recyclerviewRelativeLayout?.visibility = View.VISIBLE
            alertNoFavoriteRelativeLayout?.visibility = View.GONE
        }
    }

    override fun callbackSuccessEmptyGetMovie() {
        runOnUiThread {
            listAdapter.notifyDataSetChanged()
            recyclerviewRelativeLayout?.visibility = View.GONE
            alertNoFavoriteRelativeLayout?.visibility = View.VISIBLE
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        listAdapter.filter.filter(newText)
        return true
    }
    //endregion


    //region Private Methods
    private fun init() {
        initToolbar()
        initRecyclerView()

        presenter?.selectMovies()
    }

    private fun initToolbar() {
        toolbar?.let {
            setSupportActionBar(it)
        }
    }

    private fun initRecyclerView() {
        listAdapter = HomeListAdapter(this, presenter!!.listMovie)

        val mLayoutManager = LinearLayoutManager(this)

        movieRecyclerView?.layoutManager = mLayoutManager
        movieRecyclerView?.adapter = listAdapter
    }

    private fun goSearchActivity() {
        startActivity(SearchActivity.newInstance(this))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_down)
    }
    //endregion
}