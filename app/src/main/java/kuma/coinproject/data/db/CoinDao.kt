package kuma.coinproject.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kuma.coinproject.data.db.model.Coin

@Dao
interface CoinDao {

    @Query("SELECT * FROM COIN_TABLE")
    fun getCoinList(): Flow<List<Coin>>

    @Query("SELECT COUNT(id) FROM COIN_TABLE")
    fun getCoinCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins:List<Coin>)
}