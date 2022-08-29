package com.mironenko.internship_socket_chat.screens.login

sealed class UserAuthorizationAction {
    object None : UserAuthorizationAction()
    object ConnectToServer: UserAuthorizationAction()
    object ServerConnected : UserAuthorizationAction()
    data class Error(val error: Exception) : UserAuthorizationAction()
}