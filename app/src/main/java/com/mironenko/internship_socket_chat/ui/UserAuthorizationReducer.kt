package com.mironenko.internship_socket_chat.ui

import com.mironenko.internship_socket_chat.base.Reducer
import javax.inject.Inject

class UserAuthorizationReducer @Inject constructor() :
    Reducer<UserAuthorizationState, UserAuthorizationAction> {

    override val initialState = UserAuthorizationState(
        message = ""
    )

    override fun reduce(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationState {
        return when (action) {
            UserAuthorizationAction.None -> state
            is UserAuthorizationAction.ConnectToServer -> state.copy(
                message = "Loading..."
            )
            is UserAuthorizationAction.ServerConnected -> state.copy(
                message = action.connectionStatus
            )

            is UserAuthorizationAction.Error -> state.copy(
                message = action.error.toString()
            )
        }
    }
}