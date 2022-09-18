package com.mironenko.internship_socket_chat.data.socket.model

import java.util.*

data class ChatMessage(
    val id: String,
    val receiver: String,
    val message: String,
) {
    var messageUUID: UUID = UUID.randomUUID()
        private set
}