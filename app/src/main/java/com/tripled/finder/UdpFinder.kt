package com.tripled.finder

import android.util.Log
import com.tripled.utils.NetworkUtils
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import kotlin.concurrent.thread

class UdpFinder(
    private val listener: IFinderListener
) {
    companion object {
        private const val TAG = "UDP_FINDER"

        private const val DEFAULT_SO_TIMEOUT = 10000
        private const val UDP_SEARCH_SEND_PORT = 3438
        private const val UDP_SEARCH_RECEIVE_PORT = 3439
    }

    private var sendThread: Thread? = null
    private var receiveThread: Thread? = null
    private var isActive = false

    private var sender: DatagramSocket? = null
    private var receiver: DatagramSocket? = null

    fun setup(): Exception? {
        try {
            val broadcastAddress = NetworkUtils.getBroadcastAddress()

            sender = DatagramSocket(UDP_SEARCH_SEND_PORT)
            receiver = DatagramSocket(null).apply {
                soTimeout = DEFAULT_SO_TIMEOUT
                bind(InetSocketAddress(broadcastAddress, UDP_SEARCH_RECEIVE_PORT))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Cannot setup sockets for search")
            e.printStackTrace()
            return e
        }

        isActive = true
        return null
    }

    private fun setupSender() {
        sendThread = thread {
            try {
                while (isActive) {
                    val broadcastAddress = NetworkUtils.getBroadcastAddress()
                    val localIp = NetworkUtils.getLocalIp()
                    val localIpInBytes = localIp.split(".").map { it.toInt().toByte() }.toByteArray()
                    val command = byteArrayOf(0x02, 0x04, *localIpInBytes)
                    sender?.send(
                        DatagramPacket(
                            command,
                            command.size,
                            InetSocketAddress(broadcastAddress, UDP_SEARCH_RECEIVE_PORT)
                        )
                    )
                    Log.d(TAG, "Sent datagram")
                    Thread.sleep(2500)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Cannot send message ${e.message}")
            }
        }
    }

    private fun setupReceiver(restart: Boolean) {
        receiveThread = thread {
            do {
                try {
                    while (isActive) {
                        val localIp = NetworkUtils.getLocalIp()
                        val info = ByteArray(6)
                        val packet = DatagramPacket(info, info.size)
                        receiver?.receive(packet)
                        Log.d(TAG, "Received datagram")
                        val ipOfSender = info.takeLast(4).joinToString(".")
                        if (localIp != ipOfSender)
                            listener.onNew(ipOfSender)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Cannot read message because ${e.message}")
                }
            } while (restart)
        }
    }

    fun start(restart: Boolean = true) {
        Log.i(TAG, "Starting finder")
        setupSender()
        setupReceiver(restart)
    }

    fun stop() {
        try {
            Log.i(TAG, "Stopping finder")
            isActive = false
            sender?.close()
            receiver?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Cannot stop finder ${e.message}")
        }
    }
}