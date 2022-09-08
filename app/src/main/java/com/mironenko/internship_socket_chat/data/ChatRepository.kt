package com.mironenko.internship_socket_chat.data

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val isAuthorized: Flow<Boolean>
    suspend fun connectToServer(): String
    suspend fun userLogIn(login: String): String
}