package com.mironenko.internship_socket_chat.ui

import com.mironenko.internship_socket_chat.base.Reducer
import javax.inject.Inject

class UserAuthorizationReducer @Inject constructor() :
    Reducer<UserAuthorizationState, UserAuthorizationAction> {

    override val initialState = UserAuthorizationState()

    override fun reduce(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationState {
        return when (action) {
            UserAuthorizationAction.SingIn -> state

            //Side effect
            UserAuthorizationAction.None -> state

            UserAuthorizationAction.Authorized -> state.copy(
                authStatus = "Authorized"
            )
            UserAuthorizationAction.UnAuthorized -> state.copy(
                authStatus = "Not authorized"
            )
            UserAuthorizationAction.ConnectToServer -> state.copy(
                isProgress = true
            )
            is UserAuthorizationAction.ServerStatus -> state.copy(
                message = action.serverAddress,
                isProgress = false
            )

            is UserAuthorizationAction.Error -> state.copy(
                message = action.error.toString()
            )
        }
    }
}