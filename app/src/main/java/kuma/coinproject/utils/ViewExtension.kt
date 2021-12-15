package kuma.coinproject.utils

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity


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

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}