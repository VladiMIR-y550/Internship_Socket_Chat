package com.mironenko.internship_socket_chat.ui.chat

import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage

sealed class UserChatAction {

    object None : UserChatAction()
    object SendMessage : UserChatAction()
    data class SetMessage(val message: String) : UserChatAction()
    data class ShowSentMessage(val chatMessage: ChatMessage) : UserChatAction()
    data class NewMessageReceived(val chatMessage: ChatMessage) : UserChatAction()
    data class ShowReceivedMessage(val chatMessage: ChatMessage) : UserChatAction()
    data class Error(val error: Exception) : UserChatAction()
}