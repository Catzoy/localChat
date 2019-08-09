package com.tripled.utils.network.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager.*
import android.util.Log
import com.tripled.utils.network.NetworkUtils
import com.tripled.utils.network.NetworkUtils.WifiState.ConnectedToSpot


@Suppress("DEPRECATION")
class ConnectivityListenerApiSubN : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        val notConnected = intent.getBooleanExtra(EXTRA_NO_CONNECTIVITY, false)
        Log.d("ConnectListener", "Network is not connected = $notConnected")
        if (!notConnected) {
            val networkType = intent.getIntExtra(EXTRA_NETWORK_TYPE, TYPE_MOBILE)
            Log.d("ConnectListener", "Network type is $networkType")
            if (networkType == TYPE_WIFI) NetworkUtils.wifiState = ConnectedToSpot
        }
    }
}