package c.mathias.cryptoticker.features.ticker.data.repository

import c.mathias.cryptoticker.core.network.ConnectivityService
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import c.mathias.cryptoticker.features.ticker.data.source.db.BitfinexLocalDataSource
import c.mathias.cryptoticker.features.ticker.data.source.remote.BitfinexRemoteDataSource
import c.mathias.cryptoticker.features.ticker.domain.repository.TradingPairRepository
import javax.inject.Inject

class TradingPairRepositoryImpl @Inject constructor(
    private val bitfinexLocalDataSource: BitfinexLocalDataSource,
    private val bitfinexRemoteDataSource: BitfinexRemoteDataSource,
    private val connectivityService: ConnectivityService,
) : TradingPairRepository {

    override suspend fun getTickers(): Iterable<TradingPair> {
        return if (connectivityService.isOnline()) {
            bitfinexRemoteDataSource.getTickers().also {
                saveTickers(it)
            }
        } else {
            bitfinexLocalDataSource.getTickers()
        }
    }

    override suspend fun saveTickers(tickers: Iterable<TradingPair>) {
        bitfinexLocalDataSource.deleteAll()
        bitfinexLocalDataSource.saveTickers(tickers)
    }
}