package c.mathias.cryptoticker.features.ticker.presentation

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair

data class TickerUiState(
    val name: String,
    val tradingPairs: List<TradingPair> = emptyList()
) {

    fun build(block: Builder.() -> Unit): TickerUiState =
        Builder(this).apply(block).build()


    class Builder(state: TickerUiState) {
        var name: String = state.name
        var tradingPairs: List<TradingPair> = state.tradingPairs

        fun build(): TickerUiState = TickerUiState(
            name = name,
            tradingPairs = tradingPairs
        )
    }

}

val SUPPORTED_TICKERS = listOf(
    "tBTCUSD",
    "tETHUSD",
    "tCHSB:USD",
    "tLTCUSD",
    "tXRPUSD",
    "tDSHUSD",
    "tRRTUSD",
    "t EOSUSD",
    "tSANUSD",
    "tDATUSD",
    "tSNTUSD",
    "tDOGE:USD",
    "tLUNA:USD",
    "tMATIC:USD",
    "tNEXO :USD",
    "tOCEAN:USD",
    "tBEST:USD",
    "tAAVE:USD",
    "tPLUUSD",
    "tFILUSD",
)