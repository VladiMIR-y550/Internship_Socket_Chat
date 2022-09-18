package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage
import com.mironenko.internship_socket_chat.data.socket.model.SendMessageDto
import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersChatRepository @Inject constructor(
    private val chatSocket: ChatSocket
) : ChatRepository {

    override val isAuthorized: Flow<Boolean> = chatSocket.isAuthorized
    override val users: Flow<List<User>> = chatSocket.users
    override val messages: Flow<ChatMessage>
        get() = chatSocket.messages.map {
            ChatMessage(
                id = it.from.id,
                receiver = it.from.name,
                message = it.message
            )
        }

    override suspend fun userLogIn(login: String) {
        chatSocket.connectToServerUdp()
        chatSocket.connectToServerTcp(login = login)
    }

    override suspend fun downloadUsers() {
        chatSocket.sendGetUsers()
    }

    override suspend fun sendMessage(messageChat: ChatMessage) {
        chatSocket.sendMessage(
            SendMessageDto(
                id = messageChat.id,
                receiver = messageChat.receiver,
                message = messageChat.message
            )
        )
    }
}