package com.mironenko.internship_socket_chat.ui

sealed class UserAuthorizationAction {
    object None : UserAuthorizationAction()
    object SingIn : UserAuthorizationAction()

    //Side Effect
    object Authorized : UserAuthorizationAction()
    object UnAuthorized : UserAuthorizationAction()
    object ConnectToServer : UserAuthorizationAction()
    data class ServerStatus(val serverAddress: String) : UserAuthorizationAction()
    data class Error(val error: Exception) : UserAuthorizationAction()
}