package c.mathias.cryptoticker.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import c.mathias.cryptoticker.features.ticker.data.source.db.TradingPairDao
import c.mathias.cryptoticker.features.ticker.data.source.db.TradingPairEntity

@Database(entities = [TradingPairEntity::class], version = 1)
abstract class CryptoTickerDatabase : RoomDatabase() {
    abstract fun tradingPairDao(): TradingPairDao
}