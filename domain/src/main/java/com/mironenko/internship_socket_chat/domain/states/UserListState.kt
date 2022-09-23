package com.mironenko.internship_socket_chat.domain.states

import com.mironenko.internship_socket_chat.domain.models.User

data class UserListState(
    val userList: List<User>,
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val message: String = ""
)