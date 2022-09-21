package com.mironenko.internship_socket_chat.ui.user_list

import com.mironenko.internship_socket_chat.data.socket.model.User

sealed class UserListAction {
    object None : UserListAction()
    object LoadUsers : UserListAction()
    object Logout : UserListAction()
    object LoggedOut : UserListAction()
    data class LoadedListUsers(val users: List<User>) : UserListAction()
    data class Error(val error: Exception) : UserListAction()
}