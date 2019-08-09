package com.tripled.finder

interface IFinderListener {
    fun onError(error: Exception)

    fun onNew(ip: String)

    fun onStarted()

    fun onStopped()
}