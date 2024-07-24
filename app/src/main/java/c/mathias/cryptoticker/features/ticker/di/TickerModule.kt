package c.mathias.cryptoticker.features.ticker.di

import c.mathias.cryptoticker.core.db.CryptoTickerDatabase
import c.mathias.cryptoticker.features.ticker.data.api.BitfinexApiService
import c.mathias.cryptoticker.features.ticker.data.source.db.BitfinexLocalDataSource
import c.mathias.cryptoticker.features.ticker.data.source.db.BitfinexLocalDataSourceImpl
import c.mathias.cryptoticker.features.ticker.data.source.db.TradingPairDao
import c.mathias.cryptoticker.features.ticker.data.source.remote.BitfinexRemoteDataSource
import c.mathias.cryptoticker.features.ticker.data.source.remote.BitfinexRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TickerModule {

    @Provides
    @Singleton
    fun provideTradingPairDao(database: CryptoTickerDatabase): TradingPairDao {
        return database.tradingPairDao()
    }

    @Provides
    @Singleton
    fun provideBitfinexRemoteDataSource(bitfinexApiService: BitfinexApiService): BitfinexRemoteDataSource {
        return BitfinexRemoteDataSourceImpl(bitfinexApiService)
    }

    @Provides
    @Singleton
    fun provideBitfinexLocalDataSource(tradingPairDao: TradingPairDao): BitfinexLocalDataSource {
        return BitfinexLocalDataSourceImpl(tradingPairDao)
    }

}