package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import com.mironenko.internship_socket_chat.data.socket.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersChatRepository @Inject constructor(
    private val chatSocket: ChatSocket,
) : ChatRepository {

    override val isAuthorized: Flow<Boolean> = chatSocket.isAuthorized
    override val users: Flow<List<User>> = chatSocket.users

    override suspend fun userLogIn(login: String) {
        chatSocket.connectToServerUdp()
        chatSocket.connectToServerTcp(login = login)
    }

    override suspend fun downloadUsers() {
        chatSocket.updateUsersCycle()
    }
}