package com.mironenko.internship_socket_chat.domain.reducers

import com.mironenko.internship_socket_chat.domain.actions.UserListAction
import com.mironenko.internship_socket_chat.domain.base.Reducer
import com.mironenko.internship_socket_chat.domain.states.UserListState

class UserListReducer : Reducer<UserListState, UserListAction> {

    override val initialState = UserListState(
        userList = emptyList()
    )

    override fun reduce(state: UserListState, action: UserListAction): UserListState {
        return when (action) {
            UserListAction.None -> state
            UserListAction.Logout -> state
            UserListAction.LoggedOut -> state.copy(
                isLoggedOut = true
            )
            UserListAction.LoadUsers -> state.copy(
                isLoading = true
            )
            is UserListAction.LoadedListUsers -> state.copy(
                isLoading = false,
                userList = action.users
            )
            is UserListAction.Error -> state.copy(
                message = action.error.toString()
            )
        }
    }
}