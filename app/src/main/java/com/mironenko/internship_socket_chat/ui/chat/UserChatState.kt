package com.mironenko.internship_socket_chat.ui.chat

import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage

data class UserChatState(
    val userId: String,
    val receiverId: String,
    val sendMessage: String = "",
    val errorMessage: String = "",
    val showMessage: ChatMessage? = null,
    val isLoading: Boolean = false
)