package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.local.LocalDataSource
import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import com.mironenko.internship_socket_chat.data.socket.model.ChatMessage
import com.mironenko.internship_socket_chat.data.socket.model.SendMessageDto
import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersChatRepository @Inject constructor(
    private val chatSocket: ChatSocket,
    private val localStorage: LocalDataSource
) : ChatRepository {

    override val isAuthorized: Flow<Boolean> = chatSocket.isAuthorized
    override val users: Flow<List<User>> = chatSocket.users
    override val userId: String
        get() = chatSocket.getUserId()

    override val loginSaved: String
        get() = localStorage.loginPref

    override val messages: Flow<ChatMessage>
        get() = chatSocket.messages.map {
            ChatMessage(
                receiverId = it.from.id,
                userId = it.from.name,
                message = it.message
            )
        }

    override suspend fun logout() {
        localStorage.clearPref()
        chatSocket.logout()
    }

    override suspend fun userLogIn(login: String) {
        if (login.isNotBlank()) {
            localStorage.loginPref = login
        }
        chatSocket.connectToServerUdp()
        chatSocket.connectToServerTcp(login = localStorage.loginPref)
    }

    override suspend fun downloadUsers() {
        chatSocket.sendGetUsers()
    }

    override suspend fun sendMessage(messageChat: ChatMessage) {
        chatSocket.sendMessage(
            SendMessageDto(
                id = messageChat.receiverId,
                receiver = messageChat.userId,
                message = messageChat.message
            )
        )
    }
}