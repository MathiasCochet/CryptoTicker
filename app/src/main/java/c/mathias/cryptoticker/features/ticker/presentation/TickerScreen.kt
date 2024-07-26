package c.mathias.cryptoticker.features.ticker.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import c.mathias.cryptoticker.R
import c.mathias.cryptoticker.features.ticker.data.model.TradingPair
import kotlinx.collections.immutable.PersistentList

@Composable
fun TickerScreen(viewModel: TickerViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Crypto Ticker", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
                Image(painterResource(id = R.drawable.cloud), contentDescription = "Logo")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                ScreenContent(
                    uiState,
                    viewModel::handleEvent
                )
            }
        }
    )
}

@Composable
private fun ScreenContent(
    uiState: TickerUiState,
    handleEvent: (TickerEvent) -> Unit
) {
    val tradingPairs = uiState.tradingPairs

    if (uiState.isLoading) {
        LoadingContent()
    } else if (uiState.isError) {
        ErrorContent { handleEvent(TickerEvent.Retry) }
    } else if (tradingPairs.isNullOrEmpty()) {
        EmptyStateContent()
    } else {
        TickerListContent(tradingPairs)
    }
}

@Composable
private fun TickerListContent(tradingPairs: PersistentList<TradingPair>) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(tradingPairs.size) { index ->
            val tradingPair = tradingPairs[index]
            TickerItem(tradingPair)
        }
    }

}

@Composable
private fun TickerItem(tradingPair: TradingPair) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tradingPair.symbolName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W900,
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = tradingPair.lastPrice.toString())
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = ((tradingPair.dailyChangePerc ?: 0.0) * 100).toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if ((tradingPair.dailyChangePerc
                            ?: 0.0) > 0
                    ) Color.Green else Color.Red,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyStateContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No trading pairs available")
    }
}

@Composable
private fun ErrorContent(onRetryClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Rounded.Warning,
            tint = Color.Red,
            contentDescription = "Error"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text("Oops, something went wrong...")
        Spacer(modifier = Modifier.size(48.dp))
        Button(
            modifier = Modifier.width(150.dp),
            content = { Text("Retry") },
            onClick = onRetryClicked
        )
    }
}