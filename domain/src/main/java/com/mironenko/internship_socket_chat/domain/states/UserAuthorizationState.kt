package com.mironenko.internship_socket_chat.domain.states

data class UserAuthorizationState(
    val message: String = "",
    val authStatus: String = "",
    val login: String = "",
    val isAuth: Boolean = false,
    var isInternetAvailable: Boolean = false,
    var isProgress: Boolean = false
)