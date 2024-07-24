package c.mathias.cryptoticker.features.ticker.data.source.db

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair

interface BitfinexLocalDataSource {
    suspend fun getTickers(): Iterable<TradingPair>
    suspend fun saveTickers(tickers: Iterable<TradingPair>)
    suspend fun deleteAll()
}