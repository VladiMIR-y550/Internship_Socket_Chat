package com.mironenko.internship_socket_chat.data.socket

import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow

interface ChatSocket {
    val isAuthorized: Flow<Boolean>
    val users: Flow<List<User>>
    suspend fun connectToServerUdp()
    suspend fun connectToServerTcp(login: String)
    suspend fun updateUsersCycle()
}