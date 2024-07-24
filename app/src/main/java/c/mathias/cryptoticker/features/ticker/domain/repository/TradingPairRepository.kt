package c.mathias.cryptoticker.features.ticker.domain.repository

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair

interface TradingPairRepository {
    suspend fun getTickers(): Iterable<TradingPair>
    suspend fun saveTickers(tickers: Iterable<TradingPair>)
}