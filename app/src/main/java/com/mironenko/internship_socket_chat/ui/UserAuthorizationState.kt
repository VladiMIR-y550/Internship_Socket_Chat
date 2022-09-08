package com.mironenko.internship_socket_chat.ui

data class UserAuthorizationState(
    val message: String = "",
    val authStatus: String = "",
    val login: String = "",
    var isInternetAvailable: Boolean = false,
    var isProgress: Boolean = false
)