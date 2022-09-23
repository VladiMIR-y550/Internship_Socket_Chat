package com.mironenko.internship_socket_chat.domain.models

import java.util.*

data class ChatMessage(
    val receiverId: String,
    val userId: String,
    val message: String,
) {
    var messageUUID: UUID = UUID.randomUUID()
        private set
}