package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersChatRepository @Inject constructor(
    private val chatSocket: ChatSocket,
) : ChatRepository {

    override val isAuthorized: Flow<Boolean> = chatSocket.isAuthorized

    override suspend fun userLogIn(login: String): String {
        chatSocket.connectToServerUdp()
        return chatSocket.connectToServerTcp(login = login)
    }
}