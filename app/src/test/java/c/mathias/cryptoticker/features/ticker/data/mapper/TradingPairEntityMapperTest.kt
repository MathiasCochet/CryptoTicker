package c.mathias.cryptoticker.features.ticker.data.mapper

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import c.mathias.cryptoticker.features.ticker.data.source.db.TradingPairEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TradingPairEntityMapperTest {

    @Test
    fun `mapEntityToModel should correctly map fields from entity to model`() {
        val entity = TradingPairEntity(
            symbol = "tBTCUSD",
            bid = 66510.0,
            bidSize = 5.27418289,
            ask = 66511.0,
            askSize = 6.22327768,
            dailyChange = -362.0,
            dailyChangePerc = -0.00541325,
            lastPrice = 66511.0,
            volume = 499.91945703,
            high = 67406.0,
            low = 65547.0
        )

        val model = TradingPairEntityMapper.mapEntityToModel(entity)

        assertEquals(entity.symbol, model.symbol)
        assertEquals(entity.bid, model.bid)
        assertEquals(entity.bidSize, model.bidSize)
        assertEquals(entity.ask, model.ask)
        assertEquals(entity.askSize, model.askSize)
        assertEquals(entity.dailyChange, model.dailyChange)
        assertEquals(entity.dailyChangePerc, model.dailyChangePerc)
        assertEquals(entity.lastPrice, model.lastPrice)
        assertEquals(entity.volume, model.volume)
        assertEquals(entity.high, model.high)
        assertEquals(entity.low, model.low)
    }

    @Test
    fun `mapModelToEntity should correctly map fields from model to entity`() {
        val model = TradingPair(
            symbol = "tLTCUSD",
            bid = 73.461,
            bidSize = 776.24705061,
            ask = 73.499,
            askSize = 930.44141172,
            dailyChange = 0.994,
            dailyChangePerc = 0.01370562,
            lastPrice = null,
            volume = 3071.34000473,
            high = 73.67,
            low = 70.563
        )

        val entity = TradingPairEntityMapper.mapModelToEntity(model)

        assertEquals(model.symbol, entity.symbol)
        assertEquals(model.bid, entity.bid)
        assertEquals(model.bidSize, entity.bidSize)
        assertEquals(model.ask, entity.ask)
        assertEquals(model.askSize, entity.askSize)
        assertEquals(model.dailyChange, entity.dailyChange)
        assertEquals(model.dailyChangePerc, entity.dailyChangePerc)
        assertEquals(model.lastPrice, entity.lastPrice)
        assertEquals(model.volume, entity.volume)
        assertEquals(model.high, entity.high)
        assertEquals(model.low, entity.low)
    }
}