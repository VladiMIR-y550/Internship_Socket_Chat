package com.mironenko.internship_socket_chat.ui.chat

import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage

data class UserChatState(
    val messages: List<ChatMessage>,
    val receiverId: String,
    val sendMessage: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)