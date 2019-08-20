package com.tripled.communication.persistant

import android.util.Log
import com.tripled.communication.server.NewConnectionsServer
import com.tripled.communication.server.ServerListener
import com.tripled.db.AppDb
import com.tripled.localChat.logic.Message

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

    fun start(db: AppDb) {
        val result = connectionsServer.setup()
        if (result == null) connectionsServer.start(db.chatsDao(), db.messagesDao())
    }

    fun stop() {
        connectionsServer.stop()
    }

    fun sendMessage(message: Message) {
        connectionsServer.sendMessage(message)
    }
}