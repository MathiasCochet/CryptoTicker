package c.mathias.cryptoticker.features.ticker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun TickerScreen(viewModel: TickerViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
            BuildContent(
                uiState,
                viewModel::handleEvent
            )
            }
        }
    )
}

@Composable
private fun BuildContent(
    uiState: TickerUiState,
    handleEvent: (TickerEvent) -> Unit
) {
    Text(
        text = "Hello ${uiState.name}!",
    )
    Button(
        content = { Text("Click me!") },
        onClick = {handleEvent(TickerEvent.SomeButtonClicked)}
    )
}