package com.tripled.myapplication

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tripled.finder.FinderForegroundService
import com.tripled.utils.WifiListener
import com.tripled.utils.hasPermission
import com.tripled.utils.requestPermissions

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_LOCATION = 191
    }

    private val wifiListener = WifiListener()

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
        registerReceiver(wifiListener, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            startService(Intent(this, FinderForegroundService::class.java))
        }
    }
}
