package c.mathias.cryptoticker.features.ticker.data.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class TradingPairDtoMapperTest {

    private val response: List<List<Any?>> = listOf(
        listOf(
            "tBTCUSD",
            66510,
            5.27418289,
            66511,
            6.22327768,
            -362,
            -0.00541325,
            66511,
            499.91945703,
            67406,
            65547
        ),
        listOf(
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
        ),
        listOf(
            null,
            2145.5,
            0.0001,
            2145.6,
            0.0001,
            null,
            0.00004667,
            2145.6,
            0.0001,
            2145.6,
            2145.5
        )
    )

    @Test
    fun `should map list of trading pair fields to object`() {
        val result = TradingPairDtoMapper.mapTradingPairListDtoToTradingPair(response).toList()

        assertEquals(2, result.size)
        assertEquals("tBTCUSD", result[0].symbol)
        assertEquals(66510.0, result[0].bid)
        assertEquals(5.27418289, result[0].bidSize)
        assertEquals(66511.0, result[0].ask)
        assertEquals(6.22327768, result[0].askSize)
        assertEquals(-362.0, result[0].dailyChange)
        assertEquals(-0.00541325, result[0].dailyChangePerc)
        assertEquals(66511.0, result[0].lastPrice)
        assertEquals(499.91945703, result[0].volume)
        assertEquals(67406.0, result[0].high)
        assertEquals(65547.0, result[0].low)

        assertEquals("tLTCUSD", result[1].symbol)
        assertEquals(73.461, result[1].bid)
        assertEquals(776.24705061, result[1].bidSize)
        assertEquals(73.499, result[1].ask)
        assertEquals(930.44141172, result[1].askSize)
        assertEquals(0.994, result[1].dailyChange)
        assertEquals(0.01370562, result[1].dailyChangePerc)
        assertNull(result[1].lastPrice)
        assertEquals(3071.34000473, result[1].volume)
        assertEquals(73.67, result[1].high)
        assertEquals(70.563, result[1].low)
    }

}