package c.mathias.cryptoticker.features.ticker.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
                Image(
                    painterResource(id = getIconByAvailability(uiState.isOnline)),
                    contentDescription = "Logo"
                )
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
    } else {
        TickerListContent(
            tradingPairs,
            uiState.searchValue,
            searchTextChanged = { handleEvent(TickerEvent.Search(it)) },
            clearSearchText = { handleEvent(TickerEvent.ClearSearch) },
        )
    }
}

@Composable
private fun TickerListContent(
    tradingPairs: PersistentList<TradingPair>?,
    searchValue: String,
    searchTextChanged: (String) -> Unit,
    clearSearchText: () -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = searchValue,
        label = { Text("Search") },
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        trailingIcon = {
            SearchFieldIcon(
                searchValue.isBlank(),
                onClearPressed = clearSearchText
            )
        },
        onValueChange = searchTextChanged,
    )
    if (tradingPairs.isNullOrEmpty()) {
        EmptyStateContent()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            items(tradingPairs.size) { index ->
                val tradingPair = tradingPairs[index]
                TickerItem(tradingPair)
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun TickerItem(tradingPair: TradingPair) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium.copy(CornerSize(16.dp))),
        colors = CardDefaults.cardColors(
            containerColor = ContainerColor(priceWentUp = tradingPair.priceWentUp)
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tradingPair.symbolName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.W900,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = String.format("$%.2f", tradingPair.lastPrice),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
            Spacer(modifier = Modifier.size(8.dp))
            InfoRow(
                name = "Daily Change",
                value = String.format(
                    "%.2f%% (%.2f)",
                    tradingPair.dailyChangePercentage,
                    tradingPair.dailyChange
                )
            )
            InfoRow(
                name = "Low - High",
                value = String.format("%.2f - %.2f", tradingPair.low, tradingPair.high)
            )
            InfoRow(
                name = "Volume",
                value = String.format("%.2f", tradingPair.volume)
            )
        }
    }
}

@Composable
private fun InfoRow(
    name: String,
    value: String,

    ) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
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

@Composable
private fun ContainerColor(priceWentUp: Boolean): Color {
    return if (priceWentUp) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
}

private fun getIconByAvailability(isOnline: Boolean): Int {
    return if (isOnline) R.drawable.cloud else R.drawable.cloud_off
}

@Composable
private fun SearchFieldIcon(
    empty: Boolean,
    onClearPressed: () -> Unit,
) {
    if (empty) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = "Search",
        )
    } else {
        Icon(
            modifier = Modifier.clickable { onClearPressed() },
            imageVector = Icons.Rounded.Clear,
            contentDescription = "Clear",
        )
    }
}