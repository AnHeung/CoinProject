package kuma.coinproject.data.api

import kuma.coinproject.data.api.model.CoinDetailItem
import kuma.coinproject.data.api.model.CoinItem
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApiService {

    @GET("coins/{coin_id}")
    suspend fun getCoinDetailItem(@Path("coin_id") coinId:String): CoinDetailItem

    @GET("coins")
    suspend fun getCoinList(): List<CoinItem>
}