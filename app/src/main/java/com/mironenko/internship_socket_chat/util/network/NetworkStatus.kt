package com.mironenko.internship_socket_chat.util.network

data class NetworkStatus(
val status: Status
) {
    enum class Status {
        AVAILABLE, UNAVAILABLE
    }
}