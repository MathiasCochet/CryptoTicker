package c.mathias.cryptoticker.core.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import c.mathias.cryptoticker.core.db.CryptoTickerDatabase
import c.mathias.cryptoticker.core.network.ConnectivityService
import c.mathias.cryptoticker.core.network.ConnectivityServiceImpl
import c.mathias.cryptoticker.features.ticker.data.source.db.TradingPairDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): CryptoTickerDatabase {
        return Room.databaseBuilder(
            context,
            CryptoTickerDatabase::class.java,
            "crypto_ticker_database",
        ).build()
    }

    @Provides
    @Singleton
    fun provideTradingPairDao(database: CryptoTickerDatabase): TradingPairDao {
        return database.tradingPairDao()
    }


    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideConnectivityService(connectivityManager: ConnectivityManager): ConnectivityService =
        ConnectivityServiceImpl(connectivityManager)

}