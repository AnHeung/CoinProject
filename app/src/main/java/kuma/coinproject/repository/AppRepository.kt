package kuma.coinproject.repository

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kuma.coinproject.data.api.CoinApiService
import kuma.coinproject.data.db.CoinDao
import kuma.coinproject.data.db.model.Coin

class AppRepository(private val apiService: CoinApiService,  private val coinDao: CoinDao) {

    companion object{
         val IO = Dispatchers.IO
         val DEFAULT = Dispatchers.Default
    }

    fun fetchCoinList () = flow {
        val coinList = apiService.getCoinList()
            .map { Coin(it.id ,it.name , it.symbol,it.rank.toString())}
        emit(coinList)
        coinDao.insertCoins(coinList)
    }.flowOn(IO)

    fun fetchCoinDetailItem (coinId :String) = flow {
        val coinDetailItem = apiService.getCoinDetailItem(coinId)
        emit(coinDetailItem)
    }.flowOn(IO)


    fun getCoins() = coinDao.getCoinList().asLiveData()

}