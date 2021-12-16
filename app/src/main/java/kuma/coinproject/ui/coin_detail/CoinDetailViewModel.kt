package kuma.coinproject.ui.coin_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kuma.coinproject.data.api.model.CoinDetailItem
import kuma.coinproject.repository.AppRepository
import kuma.coinproject.ui.base.BaseViewModel
import kuma.coinproject.utils.formatDate
import kuma.coinproject.utils.orNoInformation
import kuma.coinproject.utils.orZero

class CoinDetailViewModel(private val appRepository: AppRepository) : BaseViewModel(){

    private val _coinDetailItem : MutableLiveData<CoinDetailItem> = MutableLiveData()

    val name : LiveData<String> = _coinDetailItem.map { it.name.orZero() }
    val description : LiveData<String> = _coinDetailItem.map { it.description.orNoInformation() }
    val firstDataAt : LiveData<String> = _coinDetailItem.map { it.first_data_at.formatDate() }
    //실제 값은 없지만 추후 추가가 될경우를 위한 값
    val image : LiveData<String> = _coinDetailItem.map { "ss" }

    fun getCoinDetailItem(coinId: String){
        viewModelScope.launch {
            appRepository
                .fetchCoinDetailItem(coinId)
                .onStart { progressOn() }
                .onCompletion {
                    println("완료")
                    progressOff()
                }
                .catch { cause->
                    println("catch $cause")
                    progressOff()
                }
                .collect {
                    println("완료 $it" )
                    _coinDetailItem.value = it
                }
        }
    }
}