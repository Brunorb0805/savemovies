package br.com.brb.savemovies.view.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.com.brb.savemovies.R
import br.com.brb.savemovies.view.home.HomeActivity

class SplashActivity: AppCompatActivity() {

    private val splashTimeOut = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(HomeActivity.newInstance(this))

            finish()
        }, splashTimeOut.toLong())
    }
}