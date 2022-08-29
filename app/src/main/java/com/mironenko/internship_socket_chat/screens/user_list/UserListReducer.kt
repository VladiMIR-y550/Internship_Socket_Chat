package com.mironenko.internship_socket_chat.screens.user_list

import com.mironenko.internship_socket_chat.base.Reducer

class UserListReducer: Reducer<UserListState, UserListAction> {

    override val initialState = UserListState(
        userList = emptyList()
    )

    override fun reduce(state: UserListState, action: UserListAction): UserListState {
        TODO("Not yet implemented")
    }
}