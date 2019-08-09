package com.tripled.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.hasPermission(permission: String): Boolean =
    (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)

fun Activity.requestPermissions(code: Int, vararg permissions: String) {
    ActivityCompat.requestPermissions(this, permissions, code)
}

val Context.connectivityManager: ConnectivityManager
    get() = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager