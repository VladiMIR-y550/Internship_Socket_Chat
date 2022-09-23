package com.mironenko.internship_socket_chat.domain.actions

import com.mironenko.internship_socket_chat.domain.models.User

sealed class UserListAction {
    object None : UserListAction()
    object LoadUsers : UserListAction()
    object Logout : UserListAction()
    object LoggedOut : UserListAction()
    data class LoadedListUsers(val users: List<User>) : UserListAction()
    data class Error(val error: Exception) : UserListAction()
}