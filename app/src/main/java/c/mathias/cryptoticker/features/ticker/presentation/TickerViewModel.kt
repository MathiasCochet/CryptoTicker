package c.mathias.cryptoticker.features.ticker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.mathias.cryptoticker.core.network.ConnectivityService
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
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
    private val tradingPairRepository: TradingPairRepository,
    private val connectivityService: ConnectivityService,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TickerUiState> =
        MutableStateFlow(TickerUiState(isLoading = true))
    val uiState: StateFlow<TickerUiState> = _uiState

    private var fetchJob: Job? = null

    private var tickers: List<TradingPair>? = null

    private fun setState(func: TickerUiState.Builder.() -> Unit) {
        _uiState.value = _uiState.value.build(func)
    }

    fun handleEvent(event: TickerEvent) {
        when (event) {
            is TickerEvent.Retry -> {
                setState { isLoading }
                viewModelScope.launch { fetchTickers() }
            }

            is TickerEvent.Search -> {
                setState {
                    searchValue = event.query
                    tradingPairs = tickers?.filter(event.query)?.toPersistentList()
                }

            }

            is TickerEvent.ClearSearch -> {
                setState {
                    searchValue = ""
                    tradingPairs = tickers?.toPersistentList()
                }
            }
        }
    }

    fun initialize() {
        startPeriodicFetching()
        checkNetworkChanges()
    }

    private suspend fun fetchTickers() {
        try {
            tickers = tradingPairRepository.getTickers(SUPPORTED_TICKERS).toList()
            setState {
                isLoading = false
                isError = false
                tradingPairs = tickers?.filter(searchValue)?.toPersistentList()
            }
        } catch (e: Exception) {
            setState {
                isLoading = false
                isError = true
            }
        }
    }

    private fun startPeriodicFetching() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            while (true) {
                fetchTickers()
                delay(5000)
            }
        }
    }

    public override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }

    private fun checkNetworkChanges() {
        connectivityService.isOnlineLivestream(::onNetworkChange)
    }

    private fun onNetworkChange(online: Boolean) {
        setState { isOnline = online }
    }

    private fun List<TradingPair>.filter(filter: String): List<TradingPair> {
        return this.filter {
            it.symbolName.contains(filter, ignoreCase = true)
        }
    }

}
