package com.mironenko.internship_socket_chat.domain.states

import com.mironenko.internship_socket_chat.domain.models.ChatMessage

data class UserChatState(
    val messages: List<ChatMessage>,
    val receiverId: String,
    val sendMessage: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)