package com.tripled.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.hasPermission(permission: String): Boolean =
    (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)

fun Activity.requestPermissions(code: Int, vararg permissions: String) {
    ActivityCompat.requestPermissions(this, permissions, code)
}