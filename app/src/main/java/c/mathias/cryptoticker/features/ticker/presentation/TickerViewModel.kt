package c.mathias.cryptoticker.features.ticker.presentation

import androidx.lifecycle.ViewModel
import c.mathias.cryptoticker.features.ticker.domain.repository.TradingPairRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TickerViewModel @Inject constructor(
    private val tradingPairRepository: TradingPairRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TickerUiState> =
        MutableStateFlow(TickerUiState(name = "Android"))
    val uiState: StateFlow<TickerUiState> = _uiState

    private fun setState(func: TickerUiState.Builder.() -> Unit) {
        _uiState.value = _uiState.value.build(func)
    }

    fun handleEvent(event: TickerEvent) {
        when (event) {
            TickerEvent.SomeButtonClicked -> {
                setState {
                    name = "World"
                }
            }
        }
    }

}