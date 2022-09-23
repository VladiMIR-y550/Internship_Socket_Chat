package com.mironenko.internship_socket_chat.domain.interactors.auth

import com.mironenko.internship_socket_chat.domain.ChatRepository
import com.mironenko.internship_socket_chat.domain.actions.UserAuthorizationAction
import com.mironenko.internship_socket_chat.domain.base.Interactor
import com.mironenko.internship_socket_chat.domain.states.UserAuthorizationState

class GetUserAuthorizationInteractor(
    private val chatRepository: ChatRepository
) : Interactor<UserAuthorizationState, UserAuthorizationAction> {

    override suspend fun invoke(
        state: UserAuthorizationState,
        action: UserAuthorizationAction
    ): UserAuthorizationAction {
        return when (action) {
            is UserAuthorizationAction.SingIn -> {
                chatRepository.userLogIn(login = state.login)
                UserAuthorizationAction.LoggedIn
            }
            else -> UserAuthorizationAction.Error(IllegalArgumentException("Wrong action $action"))
        }
    }

    override fun canHandle(action: UserAuthorizationAction): Boolean {
        return action is UserAuthorizationAction.SingIn
    }
}