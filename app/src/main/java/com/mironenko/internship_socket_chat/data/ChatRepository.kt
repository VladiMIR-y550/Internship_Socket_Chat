package com.mironenko.internship_socket_chat.data

interface ChatRepository {
    suspend fun connectToSocket(): String
}