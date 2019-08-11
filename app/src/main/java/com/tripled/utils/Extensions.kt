package com.tripled.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

fun Activity.hasPermission(permission: String): Boolean =
    (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)

fun Activity.requestPermissions(code: Int, vararg permissions: String) {
    ActivityCompat.requestPermissions(this, permissions, code)
}

val Context.connectivityManager: ConnectivityManager
    get() = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun sleep(millis: Long) {
    try {
        Thread.sleep(millis)
    } catch (e: Exception) {
        Log.e("TSE", "Cannot sleep ${e.message}")
    }
}