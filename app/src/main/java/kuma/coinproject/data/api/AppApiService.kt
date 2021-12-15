package kuma.coinproject.data.api

class AppApiService (private val apiService: CoinApiService){

    suspend fun getCoinList() = apiService.getCoinList()

    suspend fun getCoinDetail(coinId : String) = apiService.getCoinDetailItem(coinId)

}