package com.tripled.communication

import android.util.Log
import com.tripled.localChat.logic.User
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class Client(
    private val socket: Socket
) {
    private val input: InputStream
        get() = socket.getInputStream()
    private val output: OutputStream
        get() = socket.getOutputStream()

    fun readUserData(): User {
        val data = ByteArray(140)
        var overAllRead = 0
        do {
            val readBytes = input.read(data, overAllRead, 140)
            overAllRead += readBytes
            Log.d("Client", "Read overall bytes $overAllRead")
        } while (overAllRead != data.size)
        val name = data.copyOf(100).toString(Charsets.UTF_8)
        val id = data.copyOfRange(100, data.size).toString(Charsets.UTF_8)
        return User(id, socket.inetAddress.hostAddress, name)
    }
}