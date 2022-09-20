package com.mironenko.internship_socket_chat.ui.auth

sealed class UserAuthorizationAction {
    object None : UserAuthorizationAction()
    data class SingIn(val login: String = "") : UserAuthorizationAction()

    //Side Effect
    object Authorized : UserAuthorizationAction()
    object UnAuthorized : UserAuthorizationAction()
    object FindLogin : UserAuthorizationAction()
    object LoggedIn : UserAuthorizationAction()
    object ConnectToServer : UserAuthorizationAction()
    data class SetLogin(val login: String) : UserAuthorizationAction()
    data class Error(val error: Exception) : UserAuthorizationAction()
}