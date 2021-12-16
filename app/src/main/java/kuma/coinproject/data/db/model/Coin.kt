package kuma.coinproject.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kuma.coinproject.utils.COIN_TABLE

@Entity(tableName = COIN_TABLE)
data class Coin(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "rank") val rank: String
)