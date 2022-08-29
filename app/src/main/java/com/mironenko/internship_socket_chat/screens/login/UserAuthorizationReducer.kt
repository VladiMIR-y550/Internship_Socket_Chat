package com.mironenko.internship_socket_chat.screens.login

import com.mironenko.internship_socket_chat.base.Reducer
import javax.inject.Inject

class UserAuthorizationReducer @Inject constructor() :
    Reducer<UserAuthorizationState, UserAuthorizationAction> {

    override val initialState = UserAuthorizationState(
        isSocketConnected = false
    )

    override fun reduce(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationState {
        return when (action) {
            UserAuthorizationAction.None -> state
            UserAuthorizationAction.ConnectToServer -> state
            UserAuthorizationAction.ServerConnected -> state
            is UserAuthorizationAction.Error -> state
        }
    }
}