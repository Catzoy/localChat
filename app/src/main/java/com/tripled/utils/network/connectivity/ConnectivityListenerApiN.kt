package com.tripled.utils.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.tripled.utils.connectivityManager
import com.tripled.utils.network.NetworkUtils
import com.tripled.utils.network.NetworkUtils.WifiState.*

@Suppress("DEPRECATION")
class ConnectivityListenerApiN(
    private val context: Context
) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
        val typeOfNetwork = context.connectivityManager.getNetworkInfo(network).type
        if (typeOfNetwork == ConnectivityManager.TYPE_WIFI)
            NetworkUtils.wifiState = ConnectedToSpot
    }
}