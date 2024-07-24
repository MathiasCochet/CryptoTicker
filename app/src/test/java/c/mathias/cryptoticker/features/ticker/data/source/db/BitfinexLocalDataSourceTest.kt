package c.mathias.cryptoticker.features.ticker.data.source.db

import c.mathias.cryptoticker.features.ticker.data.mapper.TradingPairEntityMapper
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
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
class BitfinexLocalDataSourceTest {

    private lateinit var dataSource: BitfinexLocalDataSource

    @RelaxedMockK
    private lateinit var tradingPairDao: TradingPairDao

    @BeforeEach
    fun setUp() {
        dataSource = BitfinexLocalDataSourceImpl(tradingPairDao)
    }

    @Test
    fun `getTickers should return a list of TradingPair models`() = runBlocking {
        val mockEntities = listOf(
            TradingPairEntity(
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
        coEvery { tradingPairDao.getAll() } returns mockEntities

        val result = dataSource.getTickers()

        assertEquals(1, result.count())
        assertEquals("tBTCUSD", result.first().symbol)
        coVerify(exactly = 1) { tradingPairDao.getAll() }
    }

    @Test
    fun `saveTickers should save a list of TradingPair entities`() = runBlocking {
        val mockModels = listOf(
            TradingPair(
                "tLTCUSD",
                73.461,
                776.24705061,
                73.499,
                930.44141172,
                0.994,
                0.01370562,
                null,
                3071.34000473,
                73.67,
                70.563
            )
        )
        val expectedEntities = mockModels.map { TradingPairEntityMapper.mapModelToEntity(it) }

        dataSource.saveTickers(mockModels)

        coVerify(exactly = 1) { tradingPairDao.insertAll(*expectedEntities.toTypedArray()) }
    }

    @Test
    fun `deleteAll should invoke deleteAll on tradingPairDao`() = runBlocking {
        dataSource.deleteAll()

        coVerify(exactly = 1) { tradingPairDao.deleteAll() }
    }

}