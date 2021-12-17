package kuma.coinproject.ui.adapter

import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kuma.coinproject.utils.getParentActivity
import kuma.coinproject.utils.makeGone
import kuma.coinproject.utils.makeVisible
import kuma.coinproject.utils.setImageToUrl

//프로그래스바 처리를 위한 바인딩 어댑터
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

//뷰의 livedata 쪽 처리를 위해 textview 바인딩해주는 어댑터
@BindingAdapter("setText")
fun setText(view:TextView , textLiveData : LiveData<String>?){
    val parentActivity = view.getParentActivity()

    if (parentActivity != null && textLiveData != null) {
        textLiveData.observe(parentActivity, Observer {
            view.text = it
        })
    }
}

//뷰의 livedata 쪽 처리를 위해 imageview 바인딩 해주는 어댑터
@BindingAdapter("setImage")
fun setImage(imageView:ImageView , imageLiveData: LiveData<String>?){
    val parentActivity = imageView.getParentActivity()

    if (parentActivity != null && imageLiveData != null) {
        imageLiveData.observe(parentActivity , Observer { imageUrl->
            imageView.setImageToUrl(imageUrl)
        })
    }
}