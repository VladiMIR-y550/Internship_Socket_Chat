package com.mironenko.internship_socket_chat.data.socket

import com.google.gson.Gson
import com.mironenko.internship_socket_chat.data.socket.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.*
import java.net.*
import javax.inject.Inject

const val SOCKET_TIMEOUT = 10000
const val PING_TIMEOUT = 8000L

class ChatSocketClient @Inject constructor(
) : ChatSocket {
    private val gsonObj = Gson()
    private val job = SupervisorJob()
    private val clientScope = CoroutineScope(Dispatchers.Default + job)
    private var disconnectJob: Job? = null
    private var socketTCP: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null
    private var socketAddress: SocketAddress? = null

    private val _isAuthorized = MutableStateFlow(false)
    override val isAuthorized: Flow<Boolean> = _isAuthorized.asStateFlow()

    private var serverIp: String = ""
    private var userId: String = ""

    override suspend fun connectToServerUdp() {
        var isSocketAddress = false
        withContext(Dispatchers.IO) {
            val socketUdp = DatagramSocket()
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
                    socketUdp.close()
                    break
                }
            }
            _isAuthorized.value = isSocketAddress
        }
    }

    override suspend fun connectToServerTcp(login: String): String {
        socketTCP = Socket(serverIp, 6666)
        socketTCP?.soTimeout = SOCKET_TIMEOUT
        writer = PrintWriter(OutputStreamWriter(socketTCP?.getOutputStream()))
        reader = BufferedReader(InputStreamReader(socketTCP?.getInputStream()))

        clientScope.launch {
            while (socketTCP?.isConnected == true) {
                try {
                    val response = reader?.readLine() ?: continue
                    val baseDto = gsonObj.fromJson(response, BaseDto::class.java)
                    when (baseDto.action) {
                        BaseDto.Action.CONNECTED -> {
                            pingPongCycle(baseDto, login)
                        }
                        BaseDto.Action.PONG -> {
                            cancelDisconnectJob()
                        }
                        else -> {
                            throw IllegalArgumentException("Wrong BaseDto.Action")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return userId
    }

    private fun sendPing(): Job {
        return clientScope.launch {
            delay(10000)
            _isAuthorized.value = false
            clientScope.coroutineContext.cancelChildren()
            socketTCP?.close()
            socketTCP = null
        }
    }

    private fun pingPongCycle(baseDto: BaseDto, login: String): Job {
        val connectedDto = gsonObj.fromJson(baseDto.payload, ConnectedDto::class.java)
        userId = connectedDto.id
        sendJson(
            jsonBaseDto(
                baseDtoAction = BaseDto.Action.CONNECT,
                id = userId,
                login = login
            )
        )
        return clientScope.launch {
            while (socketTCP?.isConnected == true) {
                delay(PING_TIMEOUT)
                sendJson(
                    jsonBaseDto(
                        baseDtoAction = BaseDto.Action.PING,
                        id = userId
                    )
                )
                disconnectJob = sendPing()
            }
        }
    }

    private fun cancelDisconnectJob() {
        disconnectJob?.cancel()
        disconnectJob = null
    }

    private fun sendJson(baseDtoJson: String) {
        writer?.println(baseDtoJson)
        writer?.flush()
    }

    private fun jsonBaseDto(
        baseDtoAction: BaseDto.Action,
        id: String,
        login: String = ""
    ): String {
        return when (baseDtoAction) {
            BaseDto.Action.CONNECT -> {
                val payload = gsonObj.toJson(ConnectDto(id = id, name = login))
                val baseDto = BaseDto(baseDtoAction, payload)
                gsonObj.toJson(baseDto)
            }
            BaseDto.Action.PING -> {
                val payload = gsonObj.toJson(PingDto(id = id))
                val baseDto = BaseDto(baseDtoAction, payload)
                gsonObj.toJson(baseDto)
            }
            else -> {
                throw IllegalArgumentException("Wrong BaseDto.Action")
            }
        }
    }

    private fun splitSocketAddress(socketAddress: String): String {
        return socketAddress.substringAfter("/").substringBefore(":")
    }
}