package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage
import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val userId: String
    val isAuthorized: Flow<Boolean>
    val users: Flow<List<User>>
    val messages: Flow<ChatMessage>
    suspend fun userLogIn(login: String)
    suspend fun downloadUsers()
    suspend fun sendMessage(messageChat: ChatMessage)
}