package c.mathias.cryptoticker.features.ticker.data.source.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trading_pair")
data class TradingPairEntity(
    @PrimaryKey
    val symbol: String,
    @ColumnInfo(name = "bid")
    val bid: Double?,
    @ColumnInfo(name = "bid_size")
    val bidSize: Double?,
    @ColumnInfo(name = "ask")
    val ask: Double?,
    @ColumnInfo(name = "ask_size")
    val askSize: Double?,
    @ColumnInfo(name = "daily_change")
    val dailyChange: Double?,
    @ColumnInfo(name = "daily_change_perc")
    val dailyChangePerc: Double?,
    @ColumnInfo(name = "last_price")
    val lastPrice: Double?,
    @ColumnInfo(name = "volume")
    val volume: Double?,
    @ColumnInfo(name = "high")
    val high: Double?,
    @ColumnInfo(name = "low")
    val low: Double?
)

