package com.mironenko.internship_socket_chat.data.socket

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketAddress
import javax.inject.Inject

const val SOCKET_PORT_UDP = 8888
const val SOCKET_HOST = "192.168.42.62"
const val SOCKET_TIMEOUT = 10000

class ChatSocketClient @Inject constructor(
) : ChatSocket {
    private val socket = DatagramSocket()

    override suspend fun getSocketAddressByUdp(): String {
        val message = "message"
        val messageByteArray = message.toByteArray()
        socket.broadcast = true
        socket.soTimeout = SOCKET_TIMEOUT

        sendUDP(messageByteArray)
        return receiveAsyncUDP().toString()
    }

    private fun sendUDP(messageByteArray: ByteArray) {
        try {
            val packet = DatagramPacket(
                messageByteArray,
                messageByteArray.size,
                InetAddress.getByName(SOCKET_HOST),
                SOCKET_PORT_UDP
            )
            socket.send(packet)
            Log.d("TAG", "fun sendUDP() = ${packet.data}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private suspend fun receiveAsyncUDP(): SocketAddress {
        val buffer = ByteArray(256)
        val packet = DatagramPacket(buffer, buffer.size)
        var isSocketAddress = false
        val socketAddress = withContext(Dispatchers.IO) {
            async {
                while (!isSocketAddress) {
                    try {
                        runCatching {
                            socket.receive(packet)
                        }
                        isSocketAddress = true
                        Log.d("TAG", "fun receiveAsyncUDP() = ${packet.socketAddress}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                packet.socketAddress
            }
        }
        return socketAddress.await()
    }
}