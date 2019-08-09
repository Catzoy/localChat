package com.tripled.utils.network.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.tripled.utils.network.NetworkUtils
import com.tripled.utils.network.NetworkUtils.WifiState.ConnectedToSpot


class ConnectivityListenerApiSubN : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Network", "Got callback on api sub N")
        if (intent == null) return
        val notConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        if (!notConnected) NetworkUtils.wifiState = ConnectedToSpot
    }
}