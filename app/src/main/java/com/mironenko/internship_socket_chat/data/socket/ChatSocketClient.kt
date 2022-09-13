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
const val PING_TIMEOUT = 9000L
const val DISCONNECT_TIMEOUT = 8000L

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
    private var isConnectedTcp = false

    private val _isAuthorized = MutableStateFlow(false)
    override val isAuthorized: Flow<Boolean> = _isAuthorized.asStateFlow()

    private var serverIp: String = ""
    private var userId: String = ""
    private var userLogin: String = ""

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
            authorizationStatus(isSocketAddress)
        }
    }

    override suspend fun connectToServerTcp(login: String): String {
        userLogin = login
        socketTCP = Socket(serverIp, 6666)
        socketTCP?.soTimeout = SOCKET_TIMEOUT
        writer = PrintWriter(OutputStreamWriter(socketTCP?.getOutputStream()))
        reader = BufferedReader(InputStreamReader(socketTCP?.getInputStream()))
        isConnectedTcp = true
        clientScope.launch {
            while (isConnectedTcp) {
                try {
                    val response = reader?.readLine() ?: continue
                    val baseDto = gsonObj.fromJson(response, BaseDto::class.java)
                    when (baseDto.action) {
                        BaseDto.Action.CONNECTED -> {
                            saveIdFromConnectedDto(baseDto = baseDto)
                            sendConnectDto()
                            pingPongCycle()
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

    private fun saveIdFromConnectedDto(baseDto: BaseDto) {
        val connectedDto = gsonObj.fromJson(baseDto.payload, ConnectedDto::class.java)
        userId = connectedDto.id
    }

    private fun sendConnectDto() {
        sendJson(
            jsonFromBaseDto(
                action = BaseDto.Action.CONNECT,
                ConnectDto(
                    id = userId,
                    name = userLogin
                )
            )
        )
    }

    private fun pingPongCycle() {
        clientScope.launch {
            while (isConnectedTcp) {
                delay(PING_TIMEOUT)
                sendJson(
                    jsonFromBaseDto(
                        action = BaseDto.Action.PING,
                        PingDto(
                            id = userId
                        )
                    )
                )
                disconnectJob = createDisconnectJob()
            }
        }
    }

    private fun sendJson(baseDtoJson: String) {
        writer?.println(baseDtoJson)
        writer?.flush()
    }

    private fun jsonFromBaseDto(action: BaseDto.Action, payloadObj: Payload): String {
        val payload = gsonObj.toJson(payloadObj)
        val baseDto = BaseDto(action = action, payload = payload)
        return gsonObj.toJson(baseDto)
    }

    private fun splitSocketAddress(socketAddress: String): String {
        return socketAddress.substringAfter("/").substringBefore(":")
    }

    private fun authorizationStatus(status: Boolean) {
        _isAuthorized.value = status
    }

    private fun createDisconnectJob(): Job {
        return clientScope.launch {
            delay(DISCONNECT_TIMEOUT)
            authorizationStatus(false)
            clientScope.coroutineContext.cancelChildren()
            socketClose()
        }
    }

    private fun cancelDisconnectJob() {
        disconnectJob?.cancel()
        disconnectJob = null
    }

    private fun socketClose() {
        isConnectedTcp = false
        socketTCP?.close()
        socketTCP = null
    }
}