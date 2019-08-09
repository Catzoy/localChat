package com.tripled.finder

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.tripled.myapplication.MainActivity
import com.tripled.myapplication.R
import com.tripled.utils.NetworkUtils

class FinderForegroundService : IntentService(SERVICE_NAME), IFinderListener {
    companion object {
        private const val SERVICE_NAME = "FINDER_SERVICE"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "FOREGROUND_FINDER_SERVICE"
        private const val CHANNEL_NAME = "FINDER_SERVICE"
        private const val ACTION_STOP_SERVICE = "stop_ffs"

        const val ACTION_ON_UDP_NEW_IP = "udp_action_new"
        const val DATA_NEW_IP = "new_ip"

        const val ACTION_ON_UDP_ERROR = "udp_action_error"
        const val DATA_ERROR_DESCRIPTION = "error_desc"
    }

    private var finder: UdpFinder? = null
    private val stopListener = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1?.action == ACTION_STOP_SERVICE) {
                p0?.unregisterReceiver(this)
                finder?.stop()
                stopForeground(true)
            }
        }
    }

    /// Private helping functions

    private fun createStopAction(): NotificationCompat.Action {
        val stopIntent = Intent(ACTION_STOP_SERVICE).let {
            PendingIntent.getBroadcast(this, 0, it, 0)
        }
        return NotificationCompat.Action
            .Builder(R.drawable.ic_launcher_foreground, "STOP", stopIntent)
            .build()
            .also {
                registerReceiver(stopListener, IntentFilter(ACTION_STOP_SERVICE))
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val chan = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW).apply {
            lightColor = Color.BLUE
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(chan)
    }

    private fun createNotification(netmask: String, wifiSSID: String?): Notification {
        val pendingIntent = Intent(this, MainActivity::class.java).let { intent ->
            PendingIntent.getActivity(this, 0, intent, 0)
        }
        val contentText =
            if (wifiSSID != null) getString(R.string.you_are_visible_wifi, wifiSSID)
            else getString(R.string.you_are_visible_message, netmask)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setOngoing(true)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .addAction(createStopAction())
            .build()
    }

    /// Service

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Returning, since onStartCommand is called every time when startService is called
        if (finder != null || !NetworkUtils.getIsConnectedToSpot(this)) return START_NOT_STICKY
        val netmask = NetworkUtils.getLocalSubnet()
        val wifiSSID = NetworkUtils.requestWifiSSID(this)
        Log.i("FFS", "Netmask $netmask WifiSSID $wifiSSID")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = createNotification(netmask, wifiSSID)

        startForeground(NOTIFICATION_ID, notification)
        finder = UdpFinder(this).apply {
            val result = setup()
            if (result != null) onError(result)
            else start()
        }

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) return
        if (!NetworkUtils.isWifiEnabled) finder?.stop()
        else if (NetworkUtils.getIsConnectedToSpot(applicationContext)) finder?.start()
    }

    /// Finder Listener

    private fun sendToMain(action: String, vararg data: Pair<String, String>) {
        val extras = Bundle().apply { data.forEach { pair -> putString(pair.first, pair.second) } }
        sendBroadcast(Intent(action).putExtras(extras))
    }

    override fun onError(error: Exception) {
        sendToMain(ACTION_ON_UDP_ERROR, DATA_ERROR_DESCRIPTION to (error.message ?: error.toString()))
    }

    override fun onNew(ip: String) {
        sendToMain(ACTION_ON_UDP_NEW_IP, DATA_NEW_IP to ip)
    }
}