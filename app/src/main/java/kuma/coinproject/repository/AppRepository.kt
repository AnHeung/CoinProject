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

    //코인목록을 가져오고 추가로 DB에 저장하는 클래스
    fun fetchCoinList () = flow {
        val coinList = apiService.getCoinList()
            .map { Coin(it.id ,it.name , it.symbol,it.rank.toString())}
        emit(coinList)
        coinDao.insertCoins(coinList)
    }.flowOn(IO)

    //코인 상세정보 가져오기
    fun fetchCoinDetailItem (coinId :String) = flow {
        val coinDetailItem = apiService.getCoinDetailItem(coinId)
        emit(coinDetailItem)
    }.flowOn(IO)


    fun getCoins() = coinDao.getCoinList()

}