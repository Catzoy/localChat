package com.tripled.communication.server

import java.lang.Exception

interface ServerListener {
    fun onError(error: Exception)

    fun onNewConnection(ip: String)
}