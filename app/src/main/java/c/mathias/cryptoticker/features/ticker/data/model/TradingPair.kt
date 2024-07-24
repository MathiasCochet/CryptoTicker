package c.mathias.cryptoticker.features.ticker.data.model

data class TradingPair(
    val symbol: String,
    val bid: Double?,
    val bidSize: Double?,
    val ask: Double?,
    val askSize: Double?,
    val dailyChange: Double?,
    val dailyChangePerc: Double?,
    val lastPrice: Double?,
    val volume: Double?,
    val high: Double?,
    val low: Double?
)