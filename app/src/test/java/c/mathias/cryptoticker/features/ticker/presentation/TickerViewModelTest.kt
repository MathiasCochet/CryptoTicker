package c.mathias.cryptoticker.features.ticker.presentation

import app.cash.turbine.test
import c.mathias.cryptoticker.core.network.ConnectivityService
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import c.mathias.cryptoticker.features.ticker.domain.repository.TradingPairRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class TickerViewModelTest {

    @RelaxedMockK
    private lateinit var tradingPairRepository: TradingPairRepository

    @RelaxedMockK
    private lateinit var connectivityService: ConnectivityService

    private lateinit var viewModel: TickerViewModel

    private val mockTradingPairs = listOf(
        TradingPair(
            "tBTCUSD",
            66510.0,
            5.27418289,
            66511.0,
            6.22327768,
            -362.0,
            -0.00541325,
            66511.0,
            499.91945703,
            67406.0,
            65547.0
        ),
        TradingPair(
            "tETHUSD",
            2145.0,
            0.0,
            2145.0,
            0.0,
            0.0,
            0.0,
            2145.0,
            0.0,
            2145.0,
            2145.0
        )
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = TickerViewModel(
            tradingPairRepository,
            connectivityService,
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assert(initialState.isLoading)
        }
    }

    @Test
    fun `initialize should fetch tickers`() = runTest {
        coEvery { tradingPairRepository.getTickers(any()) } returns mockTradingPairs

        viewModel.uiState.test {
            viewModel.initialize()

            val initialState = awaitItem()
            assert(initialState.isLoading)

            val state = awaitItem()
            assert(!state.isLoading)
            assert(!state.isError)
            assert(state.tradingPairs == mockTradingPairs)
            coVerify(exactly = 1) { tradingPairRepository.getTickers(any()) }
        }
    }

    @Test
    fun `handleEvent should handle Retry event`() = runTest {
        coEvery { tradingPairRepository.getTickers(any()) } returns mockTradingPairs

        viewModel.uiState.test {
            viewModel.handleEvent(TickerEvent.Retry)
            val loadingState = awaitItem()
            assert(loadingState.isLoading)

            val resultState = awaitItem()
            assert(!resultState.isLoading)
            assert(!resultState.isError)
            assert(resultState.tradingPairs == mockTradingPairs)
        }
    }

    @Test
    fun `handleEvent should handle Search event`() = runTest {
        coEvery { tradingPairRepository.getTickers(any()) } returns mockTradingPairs

        viewModel.uiState.test {
            val initialState = awaitItem()
            assert(initialState.searchValue.isEmpty())
            assert(initialState.tradingPairs == null)
            assert(initialState.searchValue.isBlank())

            viewModel.initialize()

            val resultState = awaitItem()

            assert(resultState.searchValue.isEmpty())
            assert(resultState.tradingPairs == mockTradingPairs)

            viewModel.handleEvent(TickerEvent.Search("BTC"))

            val state = awaitItem()
            assert(state.searchValue == "BTC")
            assert(state.tradingPairs?.size == 1)
        }
    }

    @Test
    fun `handleEvent should handle ClearSearch event`() = runTest {
        coEvery { tradingPairRepository.getTickers(any()) } returns mockTradingPairs

        viewModel.uiState.test {
            val initialState = awaitItem()
            assert(initialState.searchValue.isEmpty())
            assert(initialState.tradingPairs == null)
            assert(initialState.searchValue.isBlank())

            viewModel.initialize()

            val resultState = awaitItem()

            assert(resultState.searchValue.isEmpty())
            assert(resultState.tradingPairs == mockTradingPairs)

            viewModel.handleEvent(TickerEvent.Search("BTC"))

            var state = awaitItem()
            assert(state.searchValue == "BTC")

            viewModel.handleEvent(TickerEvent.ClearSearch)

            state = awaitItem()
            assert(state.searchValue.isEmpty())
            assert(state.tradingPairs == mockTradingPairs)
        }
    }

    @Test
    fun `fetchTickers should update uiState on error`() = runTest {
        coEvery { tradingPairRepository.getTickers(any()) } throws Exception("Network error")

        viewModel.uiState.test {
            viewModel.initialize()

            val initialState = awaitItem()
            assert(initialState.isLoading)

            val state = awaitItem()
            assert(!state.isLoading)
            assert(state.isError)
        }
    }

    @Test
    fun `checkNetworkChanges should update uiState on network change`() = runTest {
        val captor = slot<(Boolean) -> Unit>()
        coEvery { connectivityService.isOnlineLivestream(capture(captor)) } answers {
            captor.captured.invoke(true)
            captor.captured.invoke(false)
        }

        viewModel.uiState.test {
            viewModel.initialize()
            val initialState = awaitItem()
            assert(initialState.isOnline)

            // Initial state should reflect the first network status
            var state = awaitItem()
            assert(state.isOnline)

            // State should update to reflect the second network status
            state = awaitItem()
            assert(!state.isOnline)
        }
    }
}