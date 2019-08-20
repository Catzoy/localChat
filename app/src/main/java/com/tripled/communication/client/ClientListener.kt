package com.tripled.communication.client

import com.tripled.localChat.logic.Message

interface ClientListener {
    fun onSocketClosed()

    fun onNewMessage(message: Message)

    fun onMessageSubmitted(message: Message)

    fun onMessageWritten(message: Message)
}