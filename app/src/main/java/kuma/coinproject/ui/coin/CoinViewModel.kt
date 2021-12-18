package kuma.coinproject.ui.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kuma.coinproject.data.db.model.Coin
import kuma.coinproject.repository.AppRepository
import kuma.coinproject.ui.base.BaseViewModel
import kuma.coinproject.utils.PAGING_COUNT
import timber.log.Timber

class CoinViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    private val _coinClick: MutableLiveData<Coin> = MutableLiveData()
    private val coins: Flow<List<Coin>> = appRepository.getCoins() //db쪽 데이터와 연동할 coins
     private val currentPage: MutableStateFlow<Int> = MutableStateFlow(1)
    //데이터가 내려주는값이 많아서 페이징 처리를 하기위한 pagingCoin livedata
    val pagingCoins = coins.combine(currentPage) { list, page ->
        list.take(page * PAGING_COUNT)
    }.asLiveData()

    val coinClick: LiveData<Coin> = _coinClick //코인아이템 개별 클릭 이벤트용

    init {
        getCoinList()
    }

    private fun getCoinList() {
        viewModelScope.launch {
            appRepository
                .fetchCoinList()
                .onStart { progressOn() }
                .onCompletion { throwable ->
                    throwable?.let { Timber.e("onCompletion 오류 발생 :$throwable") }
                    snackBarOn(Result.SUCCESS)
                    progressOff()
                }
                .catch { cause ->
                    Timber.e("getCoinList catch Error : $cause")
                    snackBarOn(Result.FAIL)
                    progressOff()
                }.collect()
        }
    }

    fun appendList(page: Int) {
        currentPage.value = page
    }

    fun onCoinClick(coinItem: Coin?) {
        coinItem?.let { _coinClick.value = it }
    }
}