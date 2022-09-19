package com.mironenko.internship_socket_chat.data.socket

import com.mironenko.internship_socket_chat.data.socket.model.MessageDto
import com.mironenko.internship_socket_chat.data.socket.model.SendMessageDto
import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow

interface ChatSocket {
    var userId: String
    val isAuthorized: Flow<Boolean>
    val users: Flow<List<User>>
    val messages: Flow<MessageDto>
    suspend fun connectToServerUdp()
    suspend fun connectToServerTcp(login: String)
    suspend fun sendGetUsers()
    suspend fun sendMessage(sendMessageDto: SendMessageDto)
}