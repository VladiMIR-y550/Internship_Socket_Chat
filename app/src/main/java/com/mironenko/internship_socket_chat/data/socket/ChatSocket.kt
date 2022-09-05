package com.mironenko.internship_socket_chat.data.socket

import kotlinx.coroutines.flow.Flow
import java.net.SocketAddress

interface ChatSocket {
    val isAuthorized: Flow<Boolean>
    suspend fun authorization()
    suspend fun connectToServerUdp(): String
    fun disconnect()
}