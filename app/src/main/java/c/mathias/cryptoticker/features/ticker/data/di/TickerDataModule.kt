package c.mathias.cryptoticker.features.ticker.data.di

import c.mathias.cryptoticker.features.ticker.data.repository.TradingPairRepositoryImpl
import c.mathias.cryptoticker.features.ticker.data.source.db.BitfinexLocalDataSource
import c.mathias.cryptoticker.features.ticker.data.source.db.BitfinexLocalDataSourceImpl
import c.mathias.cryptoticker.features.ticker.data.source.remote.BitfinexRemoteDataSource
import c.mathias.cryptoticker.features.ticker.data.source.remote.BitfinexRemoteDataSourceImpl
import c.mathias.cryptoticker.features.ticker.domain.repository.TradingPairRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TickerDataModule {

    @Binds
    abstract fun provideBitfinexRemoteDataSource(bitfinexRemoteDataSourceImpl: BitfinexRemoteDataSourceImpl): BitfinexRemoteDataSource

    @Binds
    abstract fun provideBitfinexLocalDataSource(bitfinexLocalDataSourceImpl: BitfinexLocalDataSourceImpl): BitfinexLocalDataSource

    @Binds
    abstract fun bindTradingPairRepository(tradingPairRepository: TradingPairRepositoryImpl): TradingPairRepository

}