package c.mathias.cryptoticker.features.ticker.presentation

import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import kotlinx.collections.immutable.PersistentList

data class TickerUiState(
    val tradingPairs: PersistentList<TradingPair>? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isOnline: Boolean = true,
    val searchValue: String = "",
) {

    fun build(block: Builder.() -> Unit): TickerUiState = Builder(this).apply(block).build()

    class Builder(state: TickerUiState) {
        var tradingPairs: PersistentList<TradingPair>? = state.tradingPairs
        var isLoading: Boolean = state.isLoading
        var isError: Boolean = state.isError
        var isOnline: Boolean = state.isOnline
        var searchValue: String = state.searchValue

        fun build(): TickerUiState = TickerUiState(
            tradingPairs = tradingPairs,
            isLoading = isLoading,
            isError = isError,
            isOnline = isOnline,
            searchValue = searchValue
        )
    }

}

val SUPPORTED_TICKERS = listOf(
    "tBTCUSD",
    "tETHUSD",
    "tGRTUSD",
    "tLTCUSD",
    "tXRPUSD",
    "tLINK:USD",
    "tFTMUSD",
    "tEOSUSD",
    "tSANUSD",
    "tDATUSD",
    "tSNTUSD",
    "tDOGE:USD",
    "tLUNA2:USD",
    "tMATIC:USD",
    "tOPXUSD",
    "tOCEAN:USD",
    "tBEST:USD",
    "tAAVE:USD",
    "tPLUUSD",
    "tFILUSD",
)