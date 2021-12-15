package kuma.coinproject.utils

import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@BindingAdapter("isProgress")
fun setProgress(view: View, isProgress: LiveData<Boolean>?) {

    val parentActivity = view.getParentActivity()

    if (parentActivity != null && isProgress != null) {
        isProgress.observe(parentActivity, Observer {

            view.run {
                when (it) {
                    true -> {
                        makeVisible()
                        parentActivity.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }
                    false -> {
                        makeGone()
                        parentActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }
                }
            }
        })
    }
}

@BindingAdapter("setText")
fun setText(view:TextView , textLiveData : LiveData<String>?){
    val parentActivity = view.getParentActivity()

    if (parentActivity != null && textLiveData != null) {

        textLiveData.observe(parentActivity, Observer {
            view.text = it
        })
    }
}