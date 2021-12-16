package kuma.coinproject.ui.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kuma.coinproject.data.db.model.Coin
import kuma.coinproject.repository.AppRepository
import kuma.coinproject.ui.base.BaseViewModel

class CoinViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    private val _coinClick: MutableLiveData<Coin> = MutableLiveData()

    val coins: LiveData<List<Coin>> = appRepository.getCoins()
    val coinClick: LiveData<Coin> = _coinClick

    init {
        getCoinList()
    }

    fun getCoinList(){
        viewModelScope.launch {
            appRepository
                .fetchCoinList()
                .onStart { progressOn() }
                .onCompletion { cause ->
                    cause?.let { println("완료") }
                    progressOff()
                }
                .catch { cause ->
                    println("getCoinList Exception : $cause")
                    progressOff()
                }.collect()
        }
    }

    fun onCoinClick(coinItem: Coin?) {
        coinItem?.let {
            _coinClick.value = it
        }
    }
}