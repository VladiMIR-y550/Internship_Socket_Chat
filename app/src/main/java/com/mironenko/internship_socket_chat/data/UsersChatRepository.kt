package com.mironenko.internship_socket_chat.data

import com.mironenko.internship_socket_chat.data.socket.ChatSocket
import javax.inject.Inject

class UsersChatRepository @Inject constructor(
    private val chatSocket: ChatSocket,
) : ChatRepository {

    override suspend fun connectToSocket(): String {
        return chatSocket.getSocketAddressByUdp()
    }
}