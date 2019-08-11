package br.com.brb.savemovies.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.brb.savemovies.R
import br.com.brb.savemovies.databinding.ActivityDetailBinding
import br.com.brb.savemovies.model.Movie
import br.com.brb.savemovies.util.InterpolatorCustom
import br.com.brb.savemovies.view.home.HomeActivity
import kotlinx.android.synthetic.main.activity_detail.*


class DetailsActivity : AppCompatActivity(), IDetailView {

    private var presenter: DetailPresenter? = null
    private var binding: ActivityDetailBinding? = null


    companion object {
        private const val IMDB_ID = "IMDB_ID"

        fun newInstance(context: Context, id: String): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(IMDB_ID, id)

            return intent
        }
    }

    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        presenter = DetailPresenter(this, this)

        init()

        if (savedInstanceState != null) {
            presenter?.movie = savedInstanceState.get("objectMovie") as Movie
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter?.movie?.let { outState.putSerializable("objectMovie", it) }
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

    override fun callbackSuccessGetMovie() {
        runOnUiThread {
           bind()
        }
    }

    override fun callbackErrorGetMovie(response: String?) {
        hideLoading()
    }

    override fun callbackSuccessGetLocalMovie() {
        runOnUiThread {
            bind()

            likedImageView?.setImageDrawable(
                ContextCompat.getDrawable(
                    baseContext,
                    R.drawable.ic_like
                )
            )
        }
    }

    override fun callbackSuccessDeleteMovie() {
        runOnUiThread {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)
            val interpolator = InterpolatorCustom(0.2, 20.0)
            bounceAnimation.interpolator = interpolator
            bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    likedImageView?.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.ic_no_like
                        )
                    )
                }

                override fun onAnimationEnd(animation: Animation) {}

                override fun onAnimationRepeat(animation: Animation) {}
            })

            likedImageView?.startAnimation(bounceAnimation)

            HomeActivity.isNewItem = true
            Toast.makeText(this, resources.getString(R.string.msg_movie_delete), Toast.LENGTH_SHORT).show()

        }
    }

    override fun callbackSuccessSaveMovie() {
        runOnUiThread {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)
            val interpolator = InterpolatorCustom(0.2, 20.0)
            bounceAnimation.interpolator = interpolator
            bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    likedImageView?.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.ic_like
                        )
                    )
                }

                override fun onAnimationEnd(animation: Animation) {}

                override fun onAnimationRepeat(animation: Animation) {}
            })

            likedImageView?.startAnimation(bounceAnimation)

            HomeActivity.isNewItem = true
            Toast.makeText(this, resources.getString(R.string.msg_movie_saved), Toast.LENGTH_SHORT).show()
        }
    }

    override fun callbackNoInternet() {
        likedImageView?.isEnabled = false
        mainScrollView?.visibility = View.GONE
        alertNoInternetRelativeLayout!!.visibility = View.VISIBLE
        hideLoading()
    }

    override fun showLoading() {
        mainScrollView?.visibility = View.GONE
        progressRelativeLayout?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressRelativeLayout?.visibility = View.GONE
    }
    //endregion


    private fun init() {
        getExtras()
        initToolbar()
        initActions()
        presenter?.loadMovieInfo()
    }

    private fun getExtras() {
        intent.extras?.let { bundle ->
            bundle.getString(IMDB_ID)?.let { imdb ->
                presenter?.imdbId = imdb
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar as Toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    private fun initActions() {
        likedImageView?.setOnClickListener { saveOrDeleteMovie() }
    }

    private fun saveOrDeleteMovie() {
        if (progressRelativeLayout?.visibility == View.GONE) {
            presenter?.saveOrDeleteMovie()

        } else {
            Toast.makeText(this, resources.getString(R.string.msg_movie_wait), Toast.LENGTH_SHORT).show()
        }
    }

    private fun bind() {
        binding?.movie = presenter?.movie
        mainScrollView?.visibility = View.VISIBLE
        hideLoading()
    }
}
