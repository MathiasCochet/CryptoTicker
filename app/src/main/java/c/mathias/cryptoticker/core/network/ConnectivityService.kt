package c.mathias.cryptoticker.core.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

interface ConnectivityService {
    fun isOnline(): Boolean
}

class ConnectivityServiceImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityService {
    override fun isOnline(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                (
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                        )
    }
}