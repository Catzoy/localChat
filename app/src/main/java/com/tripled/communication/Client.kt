package com.tripled.communication

import android.util.Log
import com.tripled.localChat.logic.User
import com.tripled.utils.sleep
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class Client(
    private val socket: Socket,
    private val clientListener: ClientListener
) {
    companion object {
        private const val TAG = "Client"
    }

    private val input: InputStream
        get() = socket.getInputStream()
    private val output: OutputStream
        get() = socket.getOutputStream()

    private val sendExecutor by lazy { Executors.newSingleThreadExecutor() }
    private val ip = socket.inetAddress.hostAddress
    private var receiveThread: Thread? = null

    private fun readBytes(length: Int): ByteArray? {
        try {
            val data = ByteArray(length)
            var overAllRead = 0
            do {
                val readBytes = input.read(data, overAllRead, length)
                overAllRead += readBytes
                Log.d(TAG, "Read overall bytes $overAllRead")
            } while (overAllRead != length)
            return data
        } catch (e: Exception) {
            Log.e(TAG, "Cannot read data ${e.message}")
            return null
        }
    }

    fun readUserData(): User? {
        val data = readBytes(140) ?: return null
        val name = data.copyOf(100).toString(Charsets.UTF_8)
        val id = data.copyOfRange(100, data.size).toString(Charsets.UTF_8)
        return User(id, User.UserState.Connected, ip, name)
    }

    fun startReadingMessages() {
        receiveThread = thread {
            while (!socket.isClosed) {
                try {
                    val data = readBytes(3)

                } catch (e: Exception) {
                    Log.e(TAG, "Cannot read ${e.message}")
                }
                sleep(1000)
            }
        }
    }

    fun sendMessage(message: String) {
        sendExecutor.submit {
            try {
                output.write(message.toByteArray())
                clientListener.onMessageWritten()
            } catch (e: Exception) {
                Log.e(TAG, "Cannot send message ${e.message}")
                if (socket.isClosed) clientListener.onSocketClosed()
            }
        }
    }
}