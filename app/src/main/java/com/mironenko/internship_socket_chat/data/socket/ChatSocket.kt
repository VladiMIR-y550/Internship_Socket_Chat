package com.mironenko.internship_socket_chat.data.socket

interface ChatSocket {
    suspend fun getSocketAddressByUdp(): String
}