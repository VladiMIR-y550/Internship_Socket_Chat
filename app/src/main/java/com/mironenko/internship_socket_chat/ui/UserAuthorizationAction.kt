package com.mironenko.internship_socket_chat.ui

sealed class UserAuthorizationAction {
    object None : UserAuthorizationAction()
    object SingIn : UserAuthorizationAction()

    //Side Effect
    object Authorized : UserAuthorizationAction()
    object UnAuthorized : UserAuthorizationAction()
    data class SetLogin(val login: String): UserAuthorizationAction()
    data class LoggedIn(val userId: String): UserAuthorizationAction()
    data class Error(val error: Exception) : UserAuthorizationAction()
}