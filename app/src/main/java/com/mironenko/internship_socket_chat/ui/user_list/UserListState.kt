package com.mironenko.internship_socket_chat.ui.user_list

import com.mironenko.internship_socket_chat.data.socket.model.User

data class UserListState(
    val userList: List<User>,
    val isLoading: Boolean = false,
    val message: String = ""
)