package com.mironenko.internship_socket_chat.ui

sealed class UserAuthorizationAction {
    object None : UserAuthorizationAction()
    object Authorization : UserAuthorizationAction()

    //Side Effect
    object Authorized : UserAuthorizationAction()
    object UnAuthorized : UserAuthorizationAction()
    object ConnectToServer : UserAuthorizationAction()
    object DisconnectServer : UserAuthorizationAction()
    data class ServerStatus(val serverAddress: String) : UserAuthorizationAction()
    data class NetworkUnavailable(val isInternetAvailable: Boolean) : UserAuthorizationAction()
    data class NetworkAvailable(val isInternetAvailable: Boolean) : UserAuthorizationAction()
    data class Error(val error: Exception) : UserAuthorizationAction()
}