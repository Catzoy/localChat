package com.tripled.utils.network

interface WifiStateListener {
    fun onWifiDisabled()

    fun onWifiEnabled()

    fun onConnectedToSpot()
}