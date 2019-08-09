package com.tripled.utils.network.connectivity

import android.net.ConnectivityManager
import android.net.Network
import com.tripled.utils.network.NetworkUtils
import com.tripled.utils.network.NetworkUtils.WifiState.*

class ConnectivityListenerApiN : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
        NetworkUtils.wifiState = ConnectedToSpot
    }
}