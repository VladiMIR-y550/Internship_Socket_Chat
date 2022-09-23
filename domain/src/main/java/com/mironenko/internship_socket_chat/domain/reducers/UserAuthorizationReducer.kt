package com.mironenko.internship_socket_chat.domain.reducers

import com.mironenko.internship_socket_chat.domain.actions.UserAuthorizationAction
import com.mironenko.internship_socket_chat.domain.base.Reducer
import com.mironenko.internship_socket_chat.domain.states.UserAuthorizationState

class UserAuthorizationReducer :
    Reducer<UserAuthorizationState, UserAuthorizationAction> {

    override val initialState = UserAuthorizationState()

    override fun reduce(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationState {
        return when (action) {
            is UserAuthorizationAction.SingIn -> state.copy(
                message = "Please enter your login",
                isProgress = true
            )
            UserAuthorizationAction.LoggedIn -> state.copy(
                message = "Authorization..."
            )
            //Side effect
            UserAuthorizationAction.None -> state.copy(
                message = "Please enter your login"
            )
            UserAuthorizationAction.FindLogin -> state.copy(
                message = "Find your last login"
            )
            UserAuthorizationAction.ConnectToServer -> state.copy(
                isProgress = true,
                message = "Authorization..."
            )
            UserAuthorizationAction.Authorized -> state.copy(
                authStatus = "Authorized",
                isProgress = false,
                isAuth = true
            )
            UserAuthorizationAction.UnAuthorized -> state.copy(
                authStatus = "Not authorized",
                isAuth = false
            )
            is UserAuthorizationAction.SetLogin -> state.copy(
                login = action.login
            )
            is UserAuthorizationAction.Error -> state.copy(
                message = action.error.toString()
            )
        }
    }
}