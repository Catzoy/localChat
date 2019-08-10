package com.tripled.communication

import java.lang.Exception

interface ServerListener {
    fun onError(error: Exception)

    fun onNewConnection(ip: String)
}