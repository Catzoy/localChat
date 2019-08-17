package com.tripled.localChat.ui.chats

import com.tripled.localChat.logic.User

interface ChatsReceiver {
    fun userFound(user: User)

    fun userDisconnected(ip: String)

    fun userNewMessage(ip: String)
}