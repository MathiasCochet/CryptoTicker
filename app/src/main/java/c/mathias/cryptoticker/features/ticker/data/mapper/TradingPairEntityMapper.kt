package c.mathias.cryptoticker.features.ticker.data.mapper

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import c.mathias.cryptoticker.features.ticker.data.source.db.TradingPairEntity

object TradingPairEntityMapper {
    fun mapEntityToModel(entity: TradingPairEntity): TradingPair {
        return TradingPair(
            entity.symbol,
            entity.bid,
            entity.bidSize,
            entity.ask,
            entity.askSize,
            entity.dailyChange,
            entity.dailyChangePerc,
            entity.lastPrice,
            entity.volume,
            entity.high,
            entity.low
        )
    }

    fun mapModelToEntity(model: TradingPair): TradingPairEntity {
        return TradingPairEntity(
            model.symbol,
            model.bid,
            model.bidSize,
            model.ask,
            model.askSize,
            model.dailyChange,
            model.dailyChangePerc,
            model.lastPrice,
            model.volume,
            model.high,
            model.low
        )
    }
}