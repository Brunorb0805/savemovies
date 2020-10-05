package br.com.brb.savemovies.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ActivityDetailBinding
import br.com.brb.savemovies.util.animate
import br.com.brb.savemovies.util.makeToast
import kotlinx.android.synthetic.main.activity_detail.*


class DetailsActivity : AppCompatActivity() {

    private var presenter: DetailPresenter? = null
    private var binding: ActivityDetailBinding? = null


    companion object {
        private const val IMDB_ID = "IMDB_ID"

        fun newInstance(context: Context, id: String?): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(IMDB_ID, id)

            return intent
        }
    }

    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        presenter = DetailPresenter()

        binding?.presenter = presenter

        init()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
    //endregion


    private fun init() {
        setupExtras()
        setupToolbar()
        setupListeners()
        setupModelUpdate()

        getMovie()
    }

    private fun setupExtras() {
        intent.extras?.let { bundle ->
            bundle.getString(IMDB_ID)?.let { imdb ->
                presenter?.imdbId = imdb
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar as Toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupListeners() {
        likedImageView?.setOnClickListener { saveOrDeleteMovie() }
    }


    private fun getMovie() {
        presenter?.getMovie {
            if (it) {
                likedImageView.animate(R.drawable.ic_like)
            } else {
                likedImageView.animate(R.drawable.ic_no_like)
            }
        }
    }

    private fun saveOrDeleteMovie() {
        if (progressRelativeLayout?.visibility == View.GONE) {
            presenter?.saveOrDeleteMovie(
                {
                    if (it) {
                        likedImageView.animate(R.drawable.ic_like)
                        makeToast(resources.getString(R.string.msg_movie_saved))
                    } else {
                        makeToast(resources.getString(R.string.msg_unexpected_error))
                    }
                },

                {
                    if (it) {
                        likedImageView.animate(R.drawable.ic_no_like)
                        makeToast(resources.getString(R.string.msg_movie_delete))
                    } else {
                        makeToast(resources.getString(R.string.msg_unexpected_error))
                    }
                }
            )

        } else {
            Toast.makeText(this, resources.getString(R.string.msg_movie_wait), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupModelUpdate() {
        presenter?.movieL?.observe(this, {
            binding?.movie = it
        })
    }

}
