package com.tripled.communication

import android.util.Log
import java.lang.Exception

object SocketService : ServerListener {
    private const val TAG = "SocketService"
    private val connectionsServer = NewConnectionsServer(this)
    private var receiver: DataReceiver? = null

    fun registerDataReceiver(newDataReceiver: DataReceiver) {
        receiver = newDataReceiver
    }

    fun unregisterDataReceiver() {
        receiver = null
    }

    override fun onError(error: Exception) {
        error.printStackTrace()
        Log.d(TAG, "Server is down ${error.message}")
        receiver?.onServerDown()
    }

    override fun onNewConnection(ip: String) {
        val user = connectionsServer.requestUser(ip)
        if (receiver != null && user != null) receiver?.onNewConnection(user)
    }

    fun start() {
        val result = connectionsServer.setup()
        if (result == null) connectionsServer.start()
    }

    fun stop() {
        connectionsServer.stop()
    }
}