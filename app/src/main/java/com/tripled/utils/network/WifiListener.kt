package com.tripled.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager.*
import com.tripled.utils.network.NetworkUtils.WifiState.Disabled
import com.tripled.utils.network.NetworkUtils.WifiState.Enabled

class WifiListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        val state = intent.getIntExtra(EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)
        NetworkUtils.wifiState = if (state == WIFI_STATE_ENABLED) Enabled else Disabled
    }
}