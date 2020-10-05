package br.com.brb.savemovies.view.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ActivitySearchBinding
import br.com.brb.savemovies.data.model.entity.Movie
import br.com.brb.savemovies.util.EndlessScrollListener
import br.com.brb.savemovies.view.detail.DetailsActivity
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity(), ISearchContract.View, SearchView.OnQueryTextListener {

    //region Atributes
    private var presenter: SearchPresenter? = null
    private var binding: ActivitySearchBinding? = null


    companion object {

        fun newInstance(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }
    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        presenter = SearchPresenter(this, this)

        binding?.presenter = presenter

        init()

    }

    override fun onDestroy() {
        presenter?.onDestroy()

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isSubmitButtonEnabled = false
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        forceHideKeyboard()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter?.searchMovies(newText, 0)


        return true
    }

    override fun itemClick(model: Movie) {
        startActivity(DetailsActivity.newInstance(this, model.imdbID))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out)
    }


    private fun init() {
        setupToolbar()
        setupRecyclerView()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar as Toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = ""
        }
    }

    /**
     * Inicializa os componentes da tela
     */
    private fun setupRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this)

        moviesRecyclerView?.layoutManager = mLayoutManager
        moviesRecyclerView?.adapter = presenter?.listAdapter

    }

    private fun setupListeners() {
//        searchEditText?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                page = 1
//                if (s.length >= 3) {
//                    sendRequest()
//                } /*else {
//                    presenter?.movieList?.clear()
//                    listAdapter!!.notifyDataSetChanged()
//                }*/
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })

        moviesRecyclerView?.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                presenter?.searchMovies(null, 1)
            }
        })

    }


    private fun forceHideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

        }
    }
}