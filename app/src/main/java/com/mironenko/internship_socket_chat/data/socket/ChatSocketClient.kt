package com.mironenko.internship_socket_chat.data.socket

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketAddress
import javax.inject.Inject

const val SOCKET_PORT_UDP = 8888
const val SOCKET_HOST = "192.168.42.149"
const val SOCKET_TIMEOUT = 10000

class ChatSocketClient @Inject constructor(
) : ChatSocket {
    private var socketAddress: SocketAddress? = null
    private val socket = DatagramSocket()

    private val _isAuthorized = MutableSharedFlow<Boolean>()
    override val isAuthorized: Flow<Boolean> = _isAuthorized.asSharedFlow()

    override suspend fun connectToServerUdp(): String {
        withContext(Dispatchers.IO) {
            socket.broadcast = true
            socket.soTimeout = SOCKET_TIMEOUT
            val buffer = ByteArray(256)

            val packetSend = DatagramPacket(
                buffer,
                buffer.size,
                InetAddress.getByName(SOCKET_HOST),
                SOCKET_PORT_UDP
            )
            val packetReceive = DatagramPacket(buffer, buffer.size)
            var test = false
            delay(2000)
            while (!test) {
                try {
                    socket.send(packetSend)
                    socket.receive(packetReceive)
                    test = true
                } catch (e: IOException) {
                    e.printStackTrace()
                    break
                }
                socketAddress = packetReceive.socketAddress
                Log.d("TAG", "fun UDP() = $socketAddress")
            }
            _isAuthorized.emit(true)
        }
        return socketAddress.toString()
    }

    override fun disconnect() {
        socket.broadcast = false
        socket.disconnect()
    }

    override suspend fun authorization() {
        TODO("Not yet implemented")
    }
}