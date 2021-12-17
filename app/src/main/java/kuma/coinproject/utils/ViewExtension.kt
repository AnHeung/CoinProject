package kuma.coinproject.utils

import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kuma.coinproject.R
import java.lang.Exception


fun View.getParentActivity(): AppCompatActivity? {

    val context = this.context

    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
    }
    return null
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun ImageView.setImageToUrl(imageUrl: String ) {
    try {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .error(R.drawable.no_image)
            .into(this)
    }catch (e : Exception){
        println("setImageToUrl Exception: $e")
    }
}