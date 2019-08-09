package com.tripled.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager.*
import android.util.Log
import com.tripled.finder.FinderForegroundService

class WifiListener : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        val state = intent.getIntExtra(EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)
        NetworkUtils.isWifiEnabled = state == WIFI_STATE_ENABLED
        Log.i("WifiListener", "Wifi status changed! It is now connected? ${NetworkUtils.isWifiEnabled}")
        context?.startService(Intent(context, FinderForegroundService::class.java))
    }
}