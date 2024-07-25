package c.mathias.cryptoticker.features.ticker.presentation

sealed interface TickerEvent {
    data object SomeButtonClicked: TickerEvent
}