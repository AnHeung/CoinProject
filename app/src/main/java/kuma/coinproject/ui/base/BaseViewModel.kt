package kuma.coinproject.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//프로그래스 처리 및 기타 공통적으로 쓰이는 부분을 뷰와 연동하기 위한 기본 뷰모델 클래스
open class BaseViewModel : ViewModel(){
    private val _isProgress  : MutableLiveData<Boolean> =  MutableLiveData(false)
    val isProgress  : LiveData<Boolean> =  _isProgress
    fun progressOn(){ _isProgress.value = true }
    fun progressOff(){ _isProgress.value = false }
}