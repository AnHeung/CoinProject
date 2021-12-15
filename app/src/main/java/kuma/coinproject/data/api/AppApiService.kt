package kuma.coinproject.data.api

class AppApiService (val apiService: CoinApiService){

    suspend fun getCoinList() = apiService.getCoinList()

}