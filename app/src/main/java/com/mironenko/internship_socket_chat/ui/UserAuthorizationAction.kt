package com.mironenko.internship_socket_chat.ui

import com.mironenko.internship_socket_chat.util.network.NetworkStatus

sealed class UserAuthorizationAction {
    object None : UserAuthorizationAction()
    data class ServerConnected(val connectionStatus: String) : UserAuthorizationAction()
    data class ConnectToServer(val networkStatus: NetworkStatus.Status) : UserAuthorizationAction()
    data class Error(val error: Exception) : UserAuthorizationAction()
}