package com.tripled.localChat.ui.chat

import com.tripled.localChat.logic.User

interface ChatReceiver {
    fun userFound(user: User)

    fun userDisconnected(ip: String)

    fun userNewMessage(ip: String)
}