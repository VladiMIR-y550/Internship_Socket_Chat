package com.mironenko.internship_socket_chat.data.models

import com.mironenko.internship_socket_chat.domain.models.User

data class BaseDto(val action: Action, val payload: String) {

    enum class Action {
        PING, PONG, CONNECT, CONNECTED, GET_USERS, USERS_RECEIVED, SEND_MESSAGE, NEW_MESSAGE, DISCONNECT
    }
}

data class ConnectDto(
    val id: String,
    val name: String
) : Payload

data class ConnectedDto(
    val id: String
) : Payload

data class DisconnectDto(
    val id: String,
    val code: Int
) : Payload

data class Error(
    val message: String
) : Payload

data class GetUsersDto(
    val id: String
) : Payload

data class MessageDto(
    val from: User,
    val message: String
) : Payload

data class PingDto(
    val id: String
) : Payload

data class PongDto(
    val id: String
) : Payload

data class SendMessageDto(
    val id: String,
    val receiver: String,
    val message: String
) : Payload

data class UdpDto(
    val ip: String
) : Payload

data class UsersReceivedDto(
    val users: List<User>
) : Payload