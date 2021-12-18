package kuma.coinproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kuma.coinproject.data.db.model.Coin
import kuma.coinproject.utils.COIN_DATABASE

@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class CoinRoomDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {

        @Volatile
        private var INSTANCE: CoinRoomDatabase? = null

        fun getDatabase(context: Context): CoinRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinRoomDatabase::class.java,
                    COIN_DATABASE
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}