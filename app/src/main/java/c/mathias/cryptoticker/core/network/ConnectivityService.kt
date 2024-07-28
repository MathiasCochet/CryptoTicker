package c.mathias.cryptoticker.core.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import javax.inject.Inject

interface ConnectivityService {
    fun isOnline(): Boolean
    fun isOnlineLivestream(isOnline: (online: Boolean) -> Unit)

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

    override fun isOnlineLivestream(isOnline: (online: Boolean) -> Unit) {
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline(true)
            }

            override fun onLost(network: Network) {
                isOnline(false)
            }
        })
    }
}