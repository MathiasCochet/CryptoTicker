package c.mathias.cryptoticker.features.ticker.presentation

sealed interface TickerEvent {

    data class Search(val query: String) : TickerEvent

    data object ClearSearch : TickerEvent

    data object Retry : TickerEvent

}