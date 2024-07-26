package c.mathias.cryptoticker.features.ticker.data.model

data class TradingPair(
    val symbol: String,
    val bid: Double?,
    val bidSize: Double?,
    val ask: Double?,
    val askSize: Double?,
    val dailyChange: Double?,
    val dailyChangePerc: Double?,
    val lastPrice: Double?,
    val volume: Double?,
    val high: Double?,
    val low: Double?
) {
    val symbolName: String
        get() {
            var symbolName: String = symbol
            if (symbolName.startsWith("t")) {
                symbolName = symbolName.substring(1)
            }
            if (symbolName.endsWith("USD")) {
                symbolName = symbolName.substring(0, symbolName.length - 3)
            }
            if (symbolName.contains(":")) {
                symbolName = symbolName.split(":")[0]
            }

            return symbolName
        }
}