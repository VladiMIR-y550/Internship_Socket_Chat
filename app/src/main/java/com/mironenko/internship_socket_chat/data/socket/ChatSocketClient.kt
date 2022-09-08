package com.mironenko.internship_socket_chat.data.socket

import com.google.gson.Gson
import com.mironenko.internship_socket_chat.data.socket.model.BaseDto
import com.mironenko.internship_socket_chat.data.socket.model.ConnectDto
import com.mironenko.internship_socket_chat.data.socket.model.ConnectedDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.*
import java.net.*
import javax.inject.Inject

const val SOCKET_TIMEOUT = 10000

class ChatSocketClient @Inject constructor(
) : ChatSocket {
    private var socketAddress: SocketAddress? = null
    private val socketUdp = DatagramSocket()

    private val _isAuthorized = MutableStateFlow(false)
    override val isAuthorized: Flow<Boolean> = _isAuthorized.asStateFlow()

    private var serverIp: String = ""
    private var userId: String = ""


    override suspend fun connectToServerUdp(): String {
        var isSocketAddress = false
        withContext(Dispatchers.IO) {
            socketUdp.broadcast = true
            socketUdp.soTimeout = SOCKET_TIMEOUT
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
                    socketUdp.send(packetSend)
                    socketUdp.receive(packetReceive)

                    socketAddress = packetReceive.socketAddress
                    isSocketAddress = packetReceive.data.isNotEmpty()
                    serverIp = splitSocketAddress(socketAddress.toString())
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    break
                }
            }
            _isAuthorized.value = isSocketAddress
        }
        return serverIp
    }

    override suspend fun connectToServerTcp(login: String): String {
        var isUserId = false
        withContext(Dispatchers.IO) {
            val socketTCP = Socket(serverIp, 6666)
            socketTCP.soTimeout = SOCKET_TIMEOUT
            val writer = PrintWriter(OutputStreamWriter(socketTCP.getOutputStream()))
            val reader = BufferedReader(InputStreamReader(socketTCP.getInputStream()))
            while (socketTCP.isConnected && !isUserId) {
                try {
                    val response = reader.readLine()
                    userId = getIdFromServer(response = response)
                    writer.println(sendOnServer(userId, login))
                    writer.flush()
                    isUserId = userId.isNotBlank()
                } catch (e: IOException) {
                    e.printStackTrace()
                    break
                }
            }
        }
        return userId
    }

    private fun getIdFromServer(response: String): String {
        val gsonObj = Gson()
        var connectedIdDto = ""
        val baseDtoObj = gsonObj.fromJson(response, BaseDto::class.java)
        if (baseDtoObj.action == BaseDto.Action.CONNECTED) {
            connectedIdDto = gsonObj.fromJson(baseDtoObj.payload, ConnectedDto::class.java).id
        }
        return connectedIdDto
    }

    private fun sendOnServer(id: String, login: String): String {
        val gsonObj = Gson()
        val action = BaseDto.Action.CONNECT
        val payload = gsonObj.toJson(
            ConnectDto(
                id = id,
                name = login
            )
        )
        val baseDto = BaseDto(action, payload)
        return gsonObj.toJson(baseDto)
    }

    private fun splitSocketAddress(socketAddress: String): String {
        return socketAddress.substringAfter("/").substringBefore(":")
    }
}