package com.mironenko.internship_socket_chat.screens.user_list

import com.mironenko.internship_socket_chat.data.User

sealed class UserListAction {
    object None : UserListAction()
    object LoadUsers : UserListAction()
    data class UsersLoaded(val users: List<User>) : UserListAction()
    data class Error(val error: Exception) : UserListAction()
}