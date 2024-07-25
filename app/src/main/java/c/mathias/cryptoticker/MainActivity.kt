package c.mathias.cryptoticker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import c.mathias.cryptoticker.core.ui.theme.CryptoTickerTheme
import c.mathias.cryptoticker.features.ticker.presentation.TickerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTickerTheme {
                TickerScreen(
                    viewModel()
                )
            }
        }
    }
}