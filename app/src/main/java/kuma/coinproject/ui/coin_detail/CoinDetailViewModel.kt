package kuma.coinproject.ui.coin_detail

import androidx.lifecycle.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kuma.coinproject.data.AppRepository
import kuma.coinproject.data.api.model.CoinDetailItem
import kuma.coinproject.utils.formatDate
import kuma.coinproject.utils.orZero

class CoinDetailViewModel(private val appRepository: AppRepository) : ViewModel(){

    private val _coinDetailItem : MutableLiveData<CoinDetailItem> = MutableLiveData()
    private val _isProgress : MutableLiveData<Boolean> = MutableLiveData(false)

    val isProgress : LiveData<Boolean> = _isProgress

    val name : LiveData<String> = _coinDetailItem.map { it.name.orZero() }
    val description : LiveData<String> = _coinDetailItem.map { it.description.orZero() }
    val firstDataAt : LiveData<String> = _coinDetailItem.map { it.first_data_at.formatDate() }

    fun getCoinDetailItem(coinId: String){
        viewModelScope.launch {
            appRepository
                .coinDetailItem(coinId)
                .onStart { _isProgress.value = true }
                .onCompletion {
                    println("완료")
                    _isProgress.value = false
                }
                .catch { cause->
                    println("catch $cause")
                    _isProgress.value = false
                }
                .collect {
                    println("완료 $it" )
                    _coinDetailItem.value = it
                }
        }
    }
}