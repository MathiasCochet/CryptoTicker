package c.mathias.cryptoticker.features.ticker.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TradingPairDao {

    @Query("SELECT * FROM trading_pair")
    suspend fun getAll(): List<TradingPairEntity>

    @Insert
    suspend fun insertAll(vararg tradingPairs: TradingPairEntity)

    @Query("DELETE FROM trading_pair")
    suspend fun deleteAll()
}