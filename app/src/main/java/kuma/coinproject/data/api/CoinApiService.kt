package kuma.coinproject.data.api

import kotlinx.coroutines.flow.Flow
import kuma.coinproject.data.api.model.CoinItem
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApiService {

    @GET("coins/{coin_name}")
    suspend fun getCoinDetailItem(@Path("coin_name") coinName:String): List<CoinItem>

    @GET("coins")
    suspend fun getCoinList(): List<CoinItem>
}