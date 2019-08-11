package br.com.brb.savemovies.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brb.savemovies.R
import br.com.brb.savemovies.util.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), ISearchView {

    //region Atributes
    private var page = 1
    private var isSearch = false

    private var listAdapter: SearchMovieListAdapter? = null
    private var presenter: SearchPresenter? = null

    companion object {

        fun newInstance(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }
    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter = SearchPresenter(this, this)

        initToolbar()
        initRecyclerView()
        action()

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

    override fun callbackSuccessSearchMovies() {
        listAdapter!!.notifyDataSetChanged()
        alertNoInternetRelativeLayout!!.visibility = View.GONE
        alertNotFoundRelativeLayout!!.visibility = View.GONE
        hideLoading()

    }

    override fun callbackErrorSearchMovies(response: String?) {
        listAdapter!!.notifyDataSetChanged()
        alertNoInternetRelativeLayout?.visibility = View.GONE
        alertNotFoundRelativeLayout?.visibility = View.VISIBLE
        hideLoading()

    }

    override fun callbackSuccessSearchMoviesPage() {
        listAdapter!!.notifyDataSetChanged()
        alertNoInternetRelativeLayout?.visibility = View.GONE
        alertNotFoundRelativeLayout.visibility = View.GONE
        hideLoading()

    }

    override fun callbackErrorSearchMoviesPage(response: String?) {
        hideLoading()
    }

    override fun callbackNoInternet() {
        listAdapter!!.notifyDataSetChanged()
        alertNoInternetRelativeLayout!!.visibility = View.VISIBLE
        alertNotFoundRelativeLayout!!.visibility = View.GONE
        hideLoading()

    }

    override fun showLoading() {
        alertNoInternetRelativeLayout!!.visibility = View.GONE
        alertNotFoundRelativeLayout!!.visibility = View.GONE
        loaderLinearLayout!!.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        isSearch = false
        loaderLinearLayout!!.visibility = View.GONE
    }


    private fun initToolbar() {
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
    private fun initRecyclerView() {
        listAdapter = SearchMovieListAdapter(this, presenter!!.movieList)

        val mLayoutManager= LinearLayoutManager(this)

        moviesRecyclerView?.layoutManager = mLayoutManager
        moviesRecyclerView?.adapter = listAdapter

    }

    private fun action() {
        searchEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                page = 1
                if (s.length >= 3) {
                    sendRequest()
                } else {
                    presenter?.movieList?.clear()
                    listAdapter!!.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        moviesRecyclerView?.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                sendRequest()
            }
        })

    }

    private fun sendRequest() {
        isSearch = true
        presenter?.searchMovies(searchEditText?.text.toString(), page)
    }

    private fun forceHideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

        }
    }
}