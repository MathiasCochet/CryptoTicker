package c.mathias.cryptoticker.core.di

import android.content.Context
import androidx.room.Room
import c.mathias.cryptoticker.core.db.CryptoTickerDatabase
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

}