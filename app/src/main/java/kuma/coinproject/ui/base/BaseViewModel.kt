package kuma.coinproject.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){

    private val _isProgress  : MutableLiveData<Boolean> =  MutableLiveData(false)

    val isProgress  : LiveData<Boolean> =  _isProgress

    fun progressOn(){ _isProgress.value = true }

    fun progressOff(){ _isProgress.value = false }
}