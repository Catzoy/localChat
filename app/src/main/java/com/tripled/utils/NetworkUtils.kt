package com.tripled.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface

object NetworkUtils {
    private const val DEFAULT_BROADCAST_NETWORK = "255.255.255.255"
    private const val DEFAULT_IP = "0.0.0.0"
    private const val UNKNOWN_WIFI_SSID = "<unknown ssid>"

    var isWifiEnabled: Boolean = false

    fun getIsConnectedToSpot(context: Context): Boolean {
        if (!isWifiEnabled) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun getBroadcastAddress(): String {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val networkInterface = interfaces.nextElement()
            if (networkInterface.isLoopback || !networkInterface.isUp)
                continue
            val broadcast = networkInterface.interfaceAddresses
                .toList()
                .mapNotNull { it.broadcast }
                .takeIf { it.isNotEmpty() }
                ?.first()
                ?.hostAddress
            if (broadcast != null) return broadcast
        }
        return DEFAULT_BROADCAST_NETWORK
    }

    fun getLocalIp(): String {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val networkInterface = interfaces.nextElement()
            if (networkInterface.isLoopback || !networkInterface.isUp)
                continue
            val ip = networkInterface.inetAddresses
                .toList()
                .filterNotNull()
                .filterIsInstance<Inet4Address>()
                .takeIf { it.isNotEmpty() }
                ?.first()
                ?.hostAddress
            if (ip != null) return ip
        }
        return DEFAULT_IP
    }

    fun getLocalSubnet(): String {
        return getLocalIp().substringBeforeLast(".")
    }

    fun requestWifiSSID(context: Context): String? {
        val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return manager.connectionInfo.ssid?.takeIf { it != UNKNOWN_WIFI_SSID }
    }
}