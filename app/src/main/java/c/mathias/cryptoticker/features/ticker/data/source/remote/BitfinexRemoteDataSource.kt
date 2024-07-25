package c.mathias.cryptoticker.features.ticker.data.source.remote

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair

interface BitfinexRemoteDataSource {
    suspend fun getTickers(tickerNames: List<String>): Iterable<TradingPair>
}