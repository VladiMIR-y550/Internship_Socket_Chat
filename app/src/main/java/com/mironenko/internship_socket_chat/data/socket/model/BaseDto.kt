package com.mironenko.internship_socket_chat.data.socket.model

import com.google.gson.annotations.SerializedName
import com.mironenko.internship_socket_chat.data.User

data class BaseDto(val action: Action, val payload: String) {

    enum class Action {
        PING, PONG, CONNECT, CONNECTED, GET_USERS, USERS_RECEIVED, SEND_MESSAGE, NEW_MESSAGE, DISCONNECT
    }
}

data class ConnectDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("age")
    val name: String
) : Payload

data class ConnectedDto(
    @SerializedName("id")
    val id: String
) : Payload

data class DisconnectDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("code")
    val code: Int
) : Payload

data class Error(
    @SerializedName("message")
    val message: String
) : Payload

data class GetUsersDto(
    @SerializedName("id")
    val id: String
) : Payload

data class MessageDto(
    @SerializedName("from")
    val from: User,
    @SerializedName("message")
    val message: String
) : Payload

data class PingDto(
    @SerializedName("id")
    val id: String
) : Payload

data class PongDto(
    @SerializedName("id")
    val id: String
) : Payload

data class SendMessageDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("receiver")
    val receiver: String,
    @SerializedName("message")
    val message: String
) : Payload

data class UdpDto(
    @SerializedName("id")
    val ip: String
) : Payload

data class UsersReceivedDto(
    @SerializedName("users")
    val users: List<User>
) : Payload

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)