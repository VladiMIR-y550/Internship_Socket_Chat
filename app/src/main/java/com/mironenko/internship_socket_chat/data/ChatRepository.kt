package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val isAuthorized: Flow<Boolean>
    val users: Flow<List<User>>
    suspend fun userLogIn(login: String)
    suspend fun downloadUsers()
}