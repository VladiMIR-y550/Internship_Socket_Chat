package com.mironenko.internship_socket_chat.data.interactor.auth

import com.mironenko.internship_socket_chat.base.Interactor
import com.mironenko.internship_socket_chat.data.ChatRepository
import com.mironenko.internship_socket_chat.ui.auth.UserAuthorizationAction
import com.mironenko.internship_socket_chat.ui.auth.UserAuthorizationState
import javax.inject.Inject

class GetUserAuthorizationInteractor @Inject constructor(
    private val repository: ChatRepository
) : Interactor<UserAuthorizationState, UserAuthorizationAction> {

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
        return when (action) {
            is UserAuthorizationAction.SingIn -> {
                repository.userLogIn(state.login)
                UserAuthorizationAction.LoggedIn
            }
            else -> UserAuthorizationAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return action is UserAuthorizationAction.SingIn
    }
}