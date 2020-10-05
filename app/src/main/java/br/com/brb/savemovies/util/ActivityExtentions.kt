package br.com.brb.savemovies.util

import android.app.Application
import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import br.com.brb.savemovies.R
import br.com.brb.savemovies.application.AppApplication


fun ImageView.animate(drawable: Int) {
    val imageView = this
    val bounceAnimation =
        AnimationUtils.loadAnimation(context, R.anim.bounce_anim)
    val interpolator = InterpolatorCustom(0.2, 20.0)
    bounceAnimation.interpolator = interpolator
    bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    drawable
                )
            )
        }

        override fun onAnimationEnd(animation: Animation) {}

        override fun onAnimationRepeat(animation: Animation) {}
    })

    imageView.startAnimation(bounceAnimation)
}

fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getContext(): Application {
    return AppApplication.applicationContext() as Application
}