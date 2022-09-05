package com.mironenko.internship_socket_chat.data.socket

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.*
import javax.inject.Inject

const val SOCKET_TIMEOUT = 10000

class ChatSocketClient @Inject constructor(
) : ChatSocket {
    private var socketAddress: SocketAddress? = null
    private val socket = DatagramSocket()

    private val _isAuthorized = MutableStateFlow(false)
    override val isAuthorized: Flow<Boolean> = _isAuthorized.asStateFlow()

    override suspend fun connectToServerUdp(): String {
        var isSocketAddress = false
        withContext(Dispatchers.IO) {
            socket.broadcast = true
            socket.soTimeout = SOCKET_TIMEOUT
            val buffer = ByteArray(256)

            val packetSend = DatagramPacket(
                buffer,
                buffer.size,
                InetAddress.getByName("255.255.255.255"),
                8888
            )
            val packetReceive = DatagramPacket(buffer, buffer.size)
            while (!isSocketAddress) {
                try {
                    socket.send(packetSend)
                    socket.receive(packetReceive)

                    socketAddress = packetReceive.socketAddress
                    isSocketAddress = packetReceive.data.isNotEmpty()
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    break
                }
            }
            _isAuthorized.value = isSocketAddress
        }
        return socketAddress.toString()
    }
}