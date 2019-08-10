package com.tripled.communication

import android.util.Log
import com.tripled.localChat.logic.User
import java.net.ServerSocket
import kotlin.concurrent.thread

class NewConnectionsServer(
    private val listener: ServerListener
) {
    companion object {
        private const val TAG = "MAIN_SS"
    }

    private var server: ServerSocket? = null
    private var acceptThread: Thread? = null
    private val sockets = mutableMapOf<String, Client>()

    fun setup(): Exception? = try {
        server = ServerSocket(4488).apply {
            soTimeout = 50000
        }
        null
    } catch (e: Exception) {
        listener.onError(e)
        e
    }

    fun start() = thread(start = false) {
        while (true) try {
            val socket = server?.accept()
            if (socket != null) {
                val host = socket.inetAddress.hostAddress
                sockets[host] = Client(socket)
                listener.onNewConnection(host)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Cannot accept connection ${e.message}")
        }
    }.let {
        acceptThread = it
        it.start()
    }

    fun stop() = try {
        server?.close()
    } catch (e: Exception) {
        Log.d(TAG, "Cannot stop server ${e.message ?: e.toString()}")
    }

    fun requestUser(ip: String): User? {
        return sockets[ip]?.readUserData()
    }
}