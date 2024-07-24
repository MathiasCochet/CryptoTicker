package c.mathias.cryptoticker.features.ticker.data.source.db

import c.mathias.cryptoticker.features.ticker.data.mapper.TradingPairEntityMapper
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import javax.inject.Inject

class BitfinexLocalDataSourceImpl @Inject constructor(
    private val tradingPairDao: TradingPairDao
) : BitfinexLocalDataSource {

    override suspend fun getTickers(): Iterable<TradingPair> {
        val result = tradingPairDao.getAll()
        return result.map { TradingPairEntityMapper.mapEntityToModel(it) }
    }

    override suspend fun saveTickers(tickers: Iterable<TradingPair>) {
        val entities = tickers.map { TradingPairEntityMapper.mapModelToEntity(it) }
        tradingPairDao.insertAll(*entities.toTypedArray())
    }

    override suspend fun deleteAll() {
        tradingPairDao.deleteAll()
    }
}