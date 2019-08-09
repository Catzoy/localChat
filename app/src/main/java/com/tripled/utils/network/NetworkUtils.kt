package com.tripled.utils.network

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import com.tripled.utils.network.NetworkUtils.WifiState.*
import java.net.Inet4Address
import java.net.NetworkInterface

object NetworkUtils {
    private const val TAG = "Network"
    private const val DEFAULT_BROADCAST_NETWORK = "255.255.255.255"
    private const val DEFAULT_IP = "0.0.0.0"
    private const val UNKNOWN_WIFI_SSID = "<unknown ssid>"

    enum class WifiState {
        Disabled,
        Enabled,
        ConnectedToSpot
    }

    internal var wifiState: WifiState = Disabled
        set(value) {
            val oldValue = field
            Log.d(TAG, "Trying to set $value for wifi state")
            field = when (value) {
                /** Callback of wifi receiver comes after connectivity listener callback */
                Enabled -> if (field == Disabled) value else ConnectedToSpot
                else -> value
            }
            Log.d(TAG, "Wifi state is $field")
            if (field == oldValue) return
            else when (field) {
                Disabled -> wifiStateListeners.forEach(WifiStateListener::onWifiDisabled)
                Enabled -> wifiStateListeners.forEach(WifiStateListener::onWifiEnabled)
                ConnectedToSpot -> wifiStateListeners.forEach(WifiStateListener::onConnectedToSpot)
            }
        }

    val isConnectedToSpot: Boolean
        get() = wifiState == ConnectedToSpot

    val isWifiEnabled: Boolean
        get() = wifiState != Disabled

    val broadcastAddress: String
        get() {
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

    val localIp: String
        get() {
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

    val localSubnet: String
        get() = localIp.substringBeforeLast(".")

    fun requestWifiSSID(context: Context): String? {
        val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return manager.connectionInfo.ssid?.takeIf { it != UNKNOWN_WIFI_SSID }
    }

    private val wifiStateListeners = mutableListOf<WifiStateListener>()

    fun registerWifiStateListener(listener: WifiStateListener) =
        wifiStateListeners.add(listener)

    fun unregisterWifiStateListener(listener: WifiStateListener) =
        wifiStateListeners.remove(listener)
}