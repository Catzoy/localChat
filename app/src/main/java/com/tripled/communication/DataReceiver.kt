package com.tripled.communication

import com.tripled.localChat.logic.User

interface DataReceiver {
    fun onServerDown()

    fun onNewMessage(ip: String)

    fun onNewConnection(user: User)
}