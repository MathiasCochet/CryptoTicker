package c.mathias.cryptoticker.features.ticker.presentation

data class TickerUiState(
    val name: String,
) {

    fun build(block: Builder.() -> Unit): TickerUiState =
        Builder(this).apply(block).build()


    class Builder(state: TickerUiState) {
        var name: String = state.name

        fun build(): TickerUiState = TickerUiState(
            name = name,
        )
    }

}