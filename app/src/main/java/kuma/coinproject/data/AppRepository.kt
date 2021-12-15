package kuma.coinproject.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kuma.coinproject.data.adapter.model.CoinAdapterItem
import kuma.coinproject.data.api.CoinApiService

class AppRepository(private val apiService: CoinApiService) {

    companion object{
         val IO = Dispatchers.IO
         val DEFAULT = Dispatchers.Default
    }

    suspend fun coinList () = flow {
        val coinList = apiService.getCoinList()
            .map { CoinAdapterItem(it.id , it.rank.toString(), it.name) }
            .toList()
        emit(coinList)
    }.flowOn(IO)

    suspend fun coinDetailItem (coinId :String) = flow {
        val coinDetailItem = apiService.getCoinDetailItem(coinId)
        emit(coinDetailItem)
    }.flowOn(IO)
}