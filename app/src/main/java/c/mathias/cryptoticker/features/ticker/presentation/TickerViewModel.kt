package c.mathias.cryptoticker.features.ticker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.mathias.cryptoticker.features.ticker.domain.repository.TradingPairRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickerViewModel @Inject constructor(
    private val tradingPairRepository: TradingPairRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TickerUiState> =
        MutableStateFlow(TickerUiState(isLoading = true))
    val uiState: StateFlow<TickerUiState> = _uiState

    private var fetchJob: Job? = null

    private fun setState(func: TickerUiState.Builder.() -> Unit) {
        _uiState.value = _uiState.value.build(func)
    }

    fun handleEvent(event: TickerEvent) {
        when (event) {
            TickerEvent.Retry -> {
                fetchTickers()
            }
        }
    }

    fun initialize() {
        fetchTickers()
        startPeriodicFetching()
    }

    private fun fetchTickers() {
        viewModelScope.launch {
            try {
                val tradingPairs = tradingPairRepository.getTickers(SUPPORTED_TICKERS)
                setState {
                    isLoading = false
                    isError = false
                    this.tradingPairs = tradingPairs.toPersistentList()
                }
            } catch (e: Exception) {
                setState {
                    isLoading = false
                    isError = true
                }
            }
        }
    }

    private fun startPeriodicFetching() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            while (true) {
                delay(5000)
                fetchTickers()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }

}