package c.mathias.cryptoticker.features.ticker.data.source.remote

import c.mathias.cryptoticker.features.ticker.data.api.BitfinexApiService
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
class BitfinexRemoteDataSourceTest {

    private lateinit var dataSource: BitfinexRemoteDataSource

    @RelaxedMockK
    private lateinit var bitfinexService: BitfinexApiService

    @BeforeEach
    fun setUp() {
        dataSource = BitfinexRemoteDataSourceImpl(bitfinexService)
    }

    @Test
    fun `getTickers should return a list of TradingPair models`() = runBlocking {
        val mockResponse = listOf(
            listOf(
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
        coEvery { bitfinexService.getTickers(emptyList()) } returns mockResponse

        val result = dataSource.getTickers(emptyList())

        assertEquals(1, result.count())
        assertEquals("tBTCUSD", result.first().symbol)
        coVerify(exactly = 1) { bitfinexService.getTickers(emptyList()) }
    }

}