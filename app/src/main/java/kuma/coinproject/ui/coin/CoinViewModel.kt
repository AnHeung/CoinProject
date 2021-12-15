package kuma.coinproject.ui.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kuma.coinproject.data.AppRepository
import kuma.coinproject.data.adapter.model.CoinAdapterItem

class CoinViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _coinList: MutableLiveData<List<CoinAdapterItem>> = MutableLiveData(listOf())
    private val _coinClick : MutableLiveData<String?>  = MutableLiveData("")
    private val _isProgress: MutableLiveData<Boolean> = MutableLiveData(false)


    val coinList: LiveData<List<CoinAdapterItem>> = _coinList
    val isProgress: LiveData<Boolean> = _isProgress
    val coinClick : LiveData<String?>  = _coinClick

    fun getCoinList() {
        viewModelScope.launch {
            appRepository.coinList()
                .onStart { _isProgress.value = true }
                .onCompletion { cause ->
                    cause?.let { println("완료") }
                    _isProgress.value = false
                }
                .catch { cause ->
                    println("getList Exception : $cause")
                    _isProgress.value = false
                }
                .collect {
                    _coinList.value = it
                    println("도착")
                }
        }
    }

    fun onCoinClick(coinName : String?){
        println("coinName: $coinName")
        coinName?.let {
            _coinClick.value = it
        }
    }
}