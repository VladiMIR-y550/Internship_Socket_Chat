package com.mironenko.internship_socket_chat.data.socket

import kotlinx.coroutines.flow.Flow

interface ChatSocket {
    val isAuthorized: Flow<Boolean>
    suspend fun connectToServerUdp(): String
}