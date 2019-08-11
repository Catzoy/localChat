package com.tripled.communication

import android.os.Message

interface ClientListener {
    fun onSocketClosed()

    fun onNewMessage(message: Message)

    fun onMessageWritten()
}