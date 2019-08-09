package com.tripled.myapplication

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tripled.finder.FinderForegroundService
import com.tripled.utils.connectivityManager
import com.tripled.utils.network.WifiListener
import com.tripled.utils.hasPermission
import com.tripled.utils.network.connectivity.ConnectivityListenerApiN
import com.tripled.utils.network.NetworkUtils
import com.tripled.utils.network.WifiStateListener
import com.tripled.utils.network.connectivity.ConnectivityListenerApiSubN
import com.tripled.utils.requestPermissions

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_LOCATION = 191
    }

    private val wifiListener = WifiListener()
    private val connectivityListener = ConnectivityListenerApiN()
    private val connectivityListenerApiSubN = ConnectivityListenerApiSubN()
    private val wifiStateListener = object : WifiStateListener {
        override fun onWifiDisabled() {
            startService(Intent(this@MainActivity, FinderForegroundService::class.java))
        }

        override fun onWifiEnabled() {
            startService(Intent(this@MainActivity, FinderForegroundService::class.java))
        }

        override fun onConnectedToSpot() {
            startService(Intent(this@MainActivity, FinderForegroundService::class.java))
        }
    }

    private fun checkLocationPermissionForNotification() {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        if (hasPermission(permission)) startService(Intent(this, FinderForegroundService::class.java))
        else requestPermissions(REQUEST_CODE_LOCATION, permission)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLocationPermissionForNotification()
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), connectivityListener)
        } else {
            registerReceiver(connectivityListenerApiSubN, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
        registerReceiver(wifiListener, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
        NetworkUtils.registerWifiStateListener(wifiStateListener)
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.unregisterNetworkCallback(connectivityListener)
        } else {
            unregisterReceiver(connectivityListenerApiSubN)
        }
        unregisterReceiver(wifiListener)
        NetworkUtils.unregisterWifiStateListener(wifiStateListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            startService(Intent(this, FinderForegroundService::class.java))
        }
    }
}
