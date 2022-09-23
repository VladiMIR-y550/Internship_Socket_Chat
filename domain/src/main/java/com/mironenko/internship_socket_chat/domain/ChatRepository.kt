package com.mironenko.internship_socket_chat.domain

import com.mironenko.internship_socket_chat.domain.models.ChatMessage
import com.mironenko.internship_socket_chat.domain.models.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val userId: String
    val loginSaved: String
    val isAuthorized: Flow<Boolean>
    val users: Flow<List<User>>
    val messages: Flow<ChatMessage>
    suspend fun logout()
    suspend fun userLogIn(login: String)
    suspend fun downloadUsers()
    suspend fun sendMessage(messageChat: ChatMessage)
}