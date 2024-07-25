package c.mathias.cryptoticker.features.ticker.domain.repository

import c.mathias.cryptoticker.core.network.ConnectivityService
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import c.mathias.cryptoticker.features.ticker.data.repository.TradingPairRepositoryImpl
import c.mathias.cryptoticker.features.ticker.data.source.db.BitfinexLocalDataSource
import c.mathias.cryptoticker.features.ticker.data.source.remote.BitfinexRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TradingPairRepositoryTest {

    private lateinit var tradingPairRepository: TradingPairRepository

    @RelaxedMockK
    private lateinit var bitfinexLocalDataSource: BitfinexLocalDataSource

    @RelaxedMockK
    private lateinit var bitfinexRemoteDataSource: BitfinexRemoteDataSource

    @RelaxedMockK
    private lateinit var connectivityService: ConnectivityService

    @BeforeEach
    fun setUp() {
        tradingPairRepository = TradingPairRepositoryImpl(
            bitfinexLocalDataSource,
            bitfinexRemoteDataSource,
            connectivityService,
        )
    }

    @Test
    fun `getTickers should return remote data when online`() = runBlocking {
        val mockTradingPairs = listOf(
            TradingPair(
                "tBTCUSD",
                66510.0,
                5.27418289,
                66511.0,
                6.22327768,
                -362.0,
                -0.00541325,
                66511.0,
                499.91945703,
                67406.0,
                65547.0
            )
        )
        coEvery { connectivityService.isOnline() } returns true
        coEvery { bitfinexRemoteDataSource.getTickers(any()) } returns mockTradingPairs

        val result = tradingPairRepository.getTickers(emptyList())

        assertEquals(mockTradingPairs, result)
        coVerify(exactly = 1) { bitfinexLocalDataSource.deleteAll() }
        coVerify(exactly = 1) { bitfinexLocalDataSource.saveTickers(mockTradingPairs) }
    }

    @Test
    fun `getTickers should return local data when offline`() = runBlocking {
        val mockTradingPairs = listOf(
            TradingPair(
                "tBTCUSD",
                66510.0,
                5.27418289,
                66511.0,
                6.22327768,
                -362.0,
                -0.00541325,
                66511.0,
                499.91945703,
                67406.0,
                65547.0
            )
        )
        coEvery { connectivityService.isOnline() } returns false
        coEvery { bitfinexLocalDataSource.getTickers() } returns mockTradingPairs

        val result = tradingPairRepository.getTickers(emptyList())

        assertEquals(mockTradingPairs, result)
        coVerify(exactly = 1) { bitfinexLocalDataSource.getTickers() }
        coVerify(exactly = 0) { bitfinexLocalDataSource.deleteAll() }
        coVerify(exactly = 0) { bitfinexLocalDataSource.saveTickers(mockTradingPairs) }
    }

}