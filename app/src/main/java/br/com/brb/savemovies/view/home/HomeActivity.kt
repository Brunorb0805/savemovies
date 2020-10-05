package br.com.brb.savemovies.view.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ActivityHomeBinding
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.view.detail.DetailsActivity
import br.com.brb.savemovies.view.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener, IHomeContract.View {

    //region Atributes
    private var presenter: HomePresenter? = null
    private var binding: ActivityHomeBinding? = null
    //endregion


    companion object {

        var isNewItem = false

        fun newInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }


    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        presenter = HomePresenter(this, this)

        binding?.presenter = presenter

        init()
    }

    override fun onResume() {
        super.onResume()

        presenter?.selectMovies()
    }

    override fun onDestroy() {
        presenter?.onDestroy()

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

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

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        presenter?.filter(newText)

        return true
    }

    override fun onItemClick(model: Movie) {
        startActivity(DetailsActivity.newInstance(this, model.imdbID))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out)
    }
    //endregion


    //region Private Methods
    private fun init() {
        initToolbar()
        initRecyclerView()
        setupListUpdate()
    }

    private fun initToolbar() {
        toolbar?.let {
            setSupportActionBar(it)
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this)

        movieRecyclerView?.layoutManager = mLayoutManager
        movieRecyclerView?.adapter = presenter?.listAdapter
    }

    private fun setupListUpdate() {
        presenter?.listMovieLive?.observe(this, {
            presenter?.setItems(it)
        })
    }

    private fun goSearchActivity() {
        startActivity(SearchActivity.newInstance(this))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_down)
    }
    //endregion

}
