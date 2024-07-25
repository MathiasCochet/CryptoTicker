package c.mathias.cryptoticker.features.ticker.data.source.remote

import c.mathias.cryptoticker.features.ticker.data.api.BitfinexApiService
import c.mathias.cryptoticker.features.ticker.data.mapper.TradingPairDtoMapper
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import javax.inject.Inject

class BitfinexRemoteDataSourceImpl @Inject constructor(
    private val bitfinexApiService: BitfinexApiService
) : BitfinexRemoteDataSource {
    override suspend fun getTickers(tickerNames: List<String>): Iterable<TradingPair> {
        return TradingPairDtoMapper.mapTradingPairListDtoToTradingPair(
            bitfinexApiService.getTickers(tickerNames)
        )
    }

}