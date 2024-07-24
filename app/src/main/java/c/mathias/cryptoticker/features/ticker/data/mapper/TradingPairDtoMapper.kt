package c.mathias.cryptoticker.features.ticker.data.mapper

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair

object TradingPairDtoMapper {

    fun mapTradingPairListDtoToTradingPair(tradingPairDto: List<List<Any?>>): Iterable<TradingPair> {
        return tradingPairDto
            .filter { !(it[0] as? String).isNullOrBlank() }
            .map { mapTradingPairDtoToTradingPair(it) }
    }

    private fun mapTradingPairDtoToTradingPair(tradingPairDto: List<Any?>): TradingPair {
        return TradingPair(
            symbol = tradingPairDto[0] as String,
            bid = tradingPairDto[1].toDoubleOrNull(),
            bidSize = tradingPairDto[2].toDoubleOrNull(),
            ask = tradingPairDto[3].toDoubleOrNull(),
            askSize = tradingPairDto[4].toDoubleOrNull(),
            dailyChange = tradingPairDto[5].toDoubleOrNull(),
            dailyChangePerc = tradingPairDto[6].toDoubleOrNull(),
            lastPrice = tradingPairDto[7].toDoubleOrNull(),
            volume = tradingPairDto[8].toDoubleOrNull(),
            high = tradingPairDto[9].toDoubleOrNull(),
            low = tradingPairDto[10].toDoubleOrNull(),
        )
    }

    private fun Any?.toDoubleOrNull(): Double? = when (this) {
        is Double -> this
        is Int -> this.toDouble()
        else -> null
    }
}